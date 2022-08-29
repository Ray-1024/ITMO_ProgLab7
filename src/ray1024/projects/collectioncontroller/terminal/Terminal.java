package ray1024.projects.collectioncontroller.terminal;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ray1024.projects.collectioncontroller.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.interfaces.Tickable;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

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

    public Terminal(IInputSource inputter, IOutputSource outputter, String CollectionFilename) throws IllegalArgumentException {
        microShells = new ArrayList<>(microShellsLimit);
        collectionController = new StudyGroupCollectionController(CollectionFilename);
        try {
            collectionController.loadCollectionFromFile();
        } catch (Exception e) {
            writer.println(e.getMessage());
        }

        reader = inputter;
        writer = outputter;
        microShells.add(new MicroShell(this, new CommandBuilder(reader, writer), true));
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
    public void tick() throws IOException {
        while (microShells.get(microShells.size() - 1).isDone()) microShells.remove(microShells.size() - 1);
        microShells.get(microShells.size() - 1).tick();
    }
}
