package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.interfaces.IExecute;
import ray1024.projects.collectioncontroller.interfaces.IInputter;
import ray1024.projects.collectioncontroller.interfaces.IOutputter;
import ray1024.projects.collectioncontroller.tools.Phrases;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Runnable {
    private final Terminal parentTerminal;
    private Scanner scanner;
    private PrintStream writer;
    private BaseCommand currentCommand;

    public MicroShell(Terminal parentTerminal, Scanner inputter, PrintStream outputter) {
        this.parentTerminal = parentTerminal;
        if (inputter == null) throw new IllegalArgumentException(Phrases.getPhrase("InputterCan'tBeNullException"));
        this.scanner = inputter;
        if (outputter == null) throw new IllegalArgumentException(Phrases.getPhrase("OutputterCan'tBeNullException"));
        writer = outputter;
    }

    @Override
    public void run() {
        while (true) {
            if (currentCommand == null) {
                writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
                try {
                    if (!scanner.hasNextLine()) break;
                    currentCommand = CommandBuilder.parseInteractiveCommand(scanner.nextLine(), this);
                } catch (IllegalStateException illegalStateException) {
                    writer.println(illegalStateException.getMessage());
                }
                continue;
            }
            if (!currentCommand.isObjectReady()) {
                writer.println(currentCommand.getStepDescription());
                try {
                    if (!scanner.hasNextLine()) break;
                    currentCommand.inputLine(scanner.nextLine());
                } catch (IllegalStateException illegalStateException) {
                    writer.println(illegalStateException.getMessage());
                }
                continue;
            }
            try {
                currentCommand.execute();
            } catch (RuntimeException e) {
                writer.println(e.getMessage());
            }
            currentCommand = null;
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

    public PrintStream getWriter() {
        return writer;
    }

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

}
