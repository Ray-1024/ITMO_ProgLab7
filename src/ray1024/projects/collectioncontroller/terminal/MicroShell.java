package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.interfaces.IExecute;
import ray1024.projects.collectioncontroller.interfaces.IInputter;
import ray1024.projects.collectioncontroller.interfaces.IOutputter;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.util.ArrayList;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Runnable {
    private final Terminal parentTerminal;
    private final IInputter inputter;
    private final IOutputter outputter;
    private BaseCommand currentCommand;

    public MicroShell(Terminal parentTerminal, IInputter Iinputter, IOutputter Ioutputter) {
        this.parentTerminal = parentTerminal;
        if (Iinputter == null) throw new IllegalArgumentException(Phrases.getPhrase("InputterCan'tBeNullException"));
        this.inputter = Iinputter;
        if (Ioutputter == null) throw new IllegalArgumentException(Phrases.getPhrase("OutputterCan'tBeNullException"));
        outputter = Ioutputter;
    }

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
            try {
                currentCommand.execute();
            } catch (RuntimeException e) {
                outputter.writeLine(e.getMessage());
            }
            currentCommand = null;
        }
    }

    public IInputter getInputter() {
        return inputter;
    }

    public IOutputter getOutputter() {
        return outputter;
    }

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

}
