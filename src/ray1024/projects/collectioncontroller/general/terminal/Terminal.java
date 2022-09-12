package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
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

    private IInputSource reader;
    private IOutputSource writer;


    public IOutputSource getWriter() {
        return writer;
    }

    public Terminal(IInputSource inputter, IOutputSource outputter) throws IllegalArgumentException {
        microShells = new ArrayList<>(microShellsLimit);
        reader = inputter;
        writer = outputter;
        try {
            microShells.add(new MicroShell(this, new CommandBuilder(reader, writer), true));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    public void setCollectionController(StudyGroupCollectionController collectionController) {
        this.collectionController = collectionController;
    }

    public void addMicroshell(MicroShell microShell) {
        if (microShells.size() == microShellsLimit)
            throw new IllegalStateException(Phrases.getPhrase("TooManyMicroshells"));
        microShells.add(microShell);
    }

    @Override
    public void tick() throws IOException {
        while (microShells.get(microShells.size() - 1).isDone()) microShells.remove(microShells.size() - 1);
        microShells.get(microShells.size() - 1).tick();
    }
}