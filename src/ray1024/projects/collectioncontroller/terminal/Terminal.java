package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.*;
import ray1024.projects.collectioncontroller.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.interfaces.IInputter;
import ray1024.projects.collectioncontroller.interfaces.IOutputter;
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
    private final IInputter inputter;
    private final IOutputter outputter;
    private BaseCommand currentCommand;

    @Override
    public void run() {
        while (true) {
            if (currentCommand == null) {
                outputter.writeLine(Phrases.getPhrase("TerminalWaitNewCommand"));
                try {
                    if (!inputter.hasGetLine()) break;
                    currentCommand = CommandBuilder.parseInteractiveCommand(inputter.getLine(), this);
                } catch (IllegalStateException illegalStateException) {
                    outputter.writeLine(illegalStateException.getMessage());
                }
                continue;
            }
            if (!currentCommand.isObjectReady()) {
                outputter.writeLine(currentCommand.getStepDescription());
                try {
                    if (!inputter.hasGetLine()) break;
                    currentCommand.inputLine(inputter.getLine());
                } catch (IllegalStateException illegalStateException) {
                    outputter.writeLine(illegalStateException.getMessage());
                }
                continue;
            }
            microShells.get(MainShell).addCommandInQueue(currentCommand);
            currentCommand = null;
            try {
                microShells.get(MainShell).execute();
            } catch (RuntimeException runtimeException) {
                outputter.writeLine(runtimeException.getMessage());
            }
        }
    }

    public IOutputter getOutputter() {
        return outputter;
    }

    public Terminal(IInputter iInputter, IOutputter iOutputter, String CollectionFilename) throws IllegalArgumentException {
        microShells = new ArrayList<>(microShellsLimit);
        currentCommand = null;

        if (iInputter == null) throw new IllegalArgumentException(Phrases.getPhrase("InputterCan'tBeNullException"));
        inputter = iInputter;
        if (iOutputter == null) throw new IllegalArgumentException(Phrases.getPhrase("OutputterCan'tBeNullException"));
        outputter = iOutputter;

        collectionController = new StudyGroupCollectionController(CollectionFilename);
        try {
            collectionController.loadCollectionFromFile();
        } catch (Exception e) {
            outputter.writeLine(e.getMessage());
        }
        microShells.add(new MicroShell(this));

        new HelpCommand(this);
        new InfoCommand(this);
        new AddCommand(this);
        new ClearCommand(this);
        new AddIfMinCommand(this);
        new ExitCommand(this);
        new ShowCommand(this);
    }


    public StudyGroupCollectionController getCollectionController() {
        return collectionController;
    }

    public void setCollectionController(StudyGroupCollectionController collectionController) {
        this.collectionController = collectionController;
    }

    public void nextLine(String line) throws Exception {
        BaseCommand baseCommand = CommandBuilder.parseInteractiveCommand(line, this);
        microShells.get(MainShell).addCommandInQueue(baseCommand);
        microShells.get(MainShell).execute();
    }

    public void addAndStartMicroShell(ArrayList<BaseCommand> forExecute) throws Exception {
        if (microShells.size() == microShellsLimit) throw new Exception(Phrases.getPhrase("CantCreateMicroshell"));
        microShells.add(new MicroShell(this));
        microShells.get(microShells.size() - 1).addCommandsInQueue(forExecute);
        microShells.get(microShells.size() - 1).execute();
        microShells.remove(microShells.size() - 1);
    }

}
