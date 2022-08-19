package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.*;
import ray1024.projects.collectioncontroller.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.interfaces.IInputter;
import ray1024.projects.collectioncontroller.interfaces.IOutputter;
import ray1024.projects.collectioncontroller.tools.ConsoleInputter;
import ray1024.projects.collectioncontroller.tools.ConsoleOutputter;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.util.ArrayList;

/**
 * Класс хранящий коллекцию и управляющий дочерними MicroShell'ами
 * Так же занимается загрузкой и сохранением коллекции
 * Максимальное колличество исполняющихся Microshell'ов : 10
 */
public class Terminal implements Runnable {
    private static final int microShellsLimit = 10;
    private static final int MainShell = 0;
    private ArrayList<MicroShell> microShells;
    private StudyGroupCollectionController collectionController;

    private IInputter inputter = new ConsoleInputter();
    private IOutputter outputter = new ConsoleOutputter();


    public IOutputter getOutputter() {
        return outputter;
    }

    public Terminal(IInputter iInputter, IOutputter iOutputter, String CollectionFilename) throws IllegalArgumentException {
        microShells = new ArrayList<>(microShellsLimit);
        collectionController = new StudyGroupCollectionController(CollectionFilename);
        try {
            collectionController.loadCollectionFromFile();
        } catch (Exception e) {
            outputter.writeLine(e.getMessage());
        }
        microShells.add(new MicroShell(this, inputter, outputter));
    }


    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    public void setCollectionController(StudyGroupCollectionController collectionController) {
        this.collectionController = collectionController;
    }

    public void createMicroshell(MicroShell microShell) {
        if (microShells.size() == microShellsLimit)
            throw new IllegalStateException(Phrases.getPhrase("TooManyMicroshells"));
        microShells.add(microShell);
    }

    @Override
    public void run() {
        microShells.get(MainShell).run();
    }
}
