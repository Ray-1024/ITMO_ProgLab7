package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * Класс хранящий коллекцию и управляющий дочерними MicroShell'ами
 * Так же занимается загрузкой и сохранением коллекции
 * Максимальное колличество исполняющихся Microshell'ов : 10
 */
public class Terminal implements Tickable, Executor {
    private static final int microShellsLimit = 10;

    @Override
    public void execute(Runnable command) {

    }

    private static final int MainShell = 0;
    private ArrayList<MicroShell> microShells;
    private StudyGroupCollectionController collectionController;
    private ICommandBuilder commandBuilder;
    private IUser master;

    public IUser getMaster() {
        return master;
    }

    public void setMaster(IUser master) {
        this.master = master;
    }

    public IOutputSource getWriter() {
        return commandBuilder.getWriter();
    }

    public Terminal(ICommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
        microShells = new ArrayList<>(microShellsLimit);
        try {
            microShells.add(new MicroShell(this, commandBuilder, true));
        } catch (Throwable ignored) {
        }
    }

    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    public MicroShell getMainMicroshell() {
        return microShells.get(MainShell);
    }

    public Terminal setCollectionController(StudyGroupCollectionController collectionController) {
        this.collectionController = collectionController;
        return this;
    }

    public void addMicroshell(MicroShell microShell) {
        if (microShells.size() == microShellsLimit)
            throw new IllegalStateException(Phrases.getPhrase("TooManyMicroshells"));
        microShells.add(microShell);
    }

    @Override
    public void tick() {
        try {
            if (microShells.size() == 0) return;
            while (microShells.size() > 1 && microShells.get(microShells.size() - 1).isDone())
                microShells.remove(microShells.size() - 1);
            if (microShells.size() == 0) return;
            microShells.get(microShells.size() - 1).tick();
        } catch (Throwable ex) {
            System.out.println("---TERMINAL EXCEPTION---");
            System.out.println(ex.getMessage());
            System.out.println("------------------------");
        }
    }
}
