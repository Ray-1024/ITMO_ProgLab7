package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.tools.Phrases;
import sun.nio.ch.ChannelInputStream;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.Channel;
import java.util.Scanner;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Runnable {
    private final Terminal parentTerminal;
    private final Scanner scanner;
    private final Channel inputChannel;
    private final PrintStream writer;
    private BaseCommand currentCommand;
    private boolean isInteractive;

    public MicroShell(Terminal parentTerminal, InputStream inputter, PrintStream outputter, boolean IsInteractive) {
        this.parentTerminal = parentTerminal;
        if (inputter == null) throw new IllegalArgumentException(Phrases.getPhrase("InputterCan'tBeNullException"));
        this.scanner = inputter;
        if (outputter == null) throw new IllegalArgumentException(Phrases.getPhrase("OutputterCan'tBeNullException"));
        writer = outputter;
        isInteractive = IsInteractive;
    }

    @Override
    public void run() {
        while (true) {
            if (currentCommand == null) {
                if (isInteractive) writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
                try {
                    if (!scanner.hasNextLine()) break;
                    currentCommand = CommandBuilder.parseInteractiveCommand(scanner.nextLine(), this);
                } catch (Throwable illegalStateException) {
                    writer.println(illegalStateException.getMessage());
                }
                continue;
            }
            if (!currentCommand.isObjectReady()) {
                if (isInteractive) writer.println(currentCommand.getStepDescription());
                try {
                    if (!scanner.hasNextLine()) break;
                    currentCommand.inputLine(scanner.nextLine());
                } catch (Throwable illegalStateException) {
                    writer.println(illegalStateException.getMessage());
                }
                continue;
            }
            try {
                currentCommand.execute();
            } catch (Throwable e) {
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
