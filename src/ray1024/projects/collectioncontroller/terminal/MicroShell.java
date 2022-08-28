package ray1024.projects.collectioncontroller.terminal;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.commands.CommandRegister;
import ray1024.projects.collectioncontroller.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Runnable {
    private final Terminal parentTerminal;
    private final IInputSource reader;
    private final IOutputSource writer;
    private BaseCommand currentCommand;
    private boolean isInteractive;

    public MicroShell(Terminal parentTerminal, IInputSource inputter, IOutputSource outputter, boolean IsInteractive) {
        this.parentTerminal = parentTerminal;
        if (inputter == null) throw new IllegalArgumentException(Phrases.getPhrase("InputterCan'tBeNullException"));
        this.reader = inputter;
        if (outputter == null) throw new IllegalArgumentException(Phrases.getPhrase("OutputterCan'tBeNullException"));
        this.writer = outputter;
        isInteractive = IsInteractive;
    }

    @Override
    public void run() {
        while (true) {
            if (currentCommand == null) {
                if (isInteractive) writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
                try {
                    if (!reader.hasNextLine()) break;
                    currentCommand = CommandRegister.getRegisteredCommandByName(reader.nextLine());
                    if(currentCommand!=null)currentCommand.setParentShell(this);
                } catch (Throwable illegalStateException) {
                    writer.println(illegalStateException.getMessage());
                }
                continue;
            }
            if (!currentCommand.isObjectReady()) {
                if (isInteractive) writer.println(currentCommand.getStepDescription());
                try {
                    if (!reader.hasNextLine()) break;
                    currentCommand.inputLine(reader.nextLine());
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

    public IInputSource getReader() {
        return reader;
    }

    public IOutputSource getWriter() {
        return writer;
    }

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

}
