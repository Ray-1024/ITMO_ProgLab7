package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.interfaces.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс хранящий коллекцию и управляющий дочерними MicroShell'ами
 * Так же занимается загрузкой и сохранением коллекции
 * Максимальное колличество исполняющихся Microshell'ов : 10
 */
public class Terminal implements Tickable {
    private static final int microShellsLimit = 10;
    private static final int MainShell = 0;
    private ArrayList<MicroShell> microShells;
    private StudyGroupCollectionController collectionController;
    private ICommandBuilder commandBuilder;


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
