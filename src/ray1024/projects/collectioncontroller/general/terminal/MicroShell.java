package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.interfaces.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Tickable {
    private final Terminal parentTerminal;
    private final ICommandBuilder commandBuilder;
    private final boolean isInteractive;

    public MicroShell(Terminal parentTerminal, ICommandBuilder _commandBuilder, boolean IsInteractive) {
        this.parentTerminal = parentTerminal;
        if (_commandBuilder == null) throw new RuntimeException(Phrases.getPhrase(""));
        commandBuilder = _commandBuilder;
        isInteractive = IsInteractive;
    }

    public ICommandBuilder getCommandBuilder() {
        return commandBuilder;
    }

    @Override
    public void tick() {
        try {
            if (commandBuilder.isDone()) return;
            commandBuilder.tick();
            if (commandBuilder.getCommand() != null) {
                commandBuilder.getCommand().setParentShell(this).execute();
                commandBuilder.reset();
            }
        } catch (Throwable ignored) {
        }
    }

    public IInputSource getReader() {
        return commandBuilder.getReader();
    }

    public IOutputSource getWriter() {
        return commandBuilder.getWriter();
    }

    public Terminal getParentTerminal() {
        return parentTerminal;
    }

    public boolean isDone() {
        try {
            return commandBuilder.isDone();
        } catch (Throwable ex) {
            return true;
        }
    }
}
