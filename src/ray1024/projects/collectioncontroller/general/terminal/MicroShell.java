package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.ICommandBuilder;
import ray1024.projects.collectioncontroller.general.readers.IInputSource;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.IOutputSource;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Tickable, Runnable {
    private final Terminal parentTerminal;
    private final ICommandBuilder commandBuilder;
    private final boolean isInteractive;

    public MicroShell(Terminal parentTerminal, ICommandBuilder _commandBuilder, boolean IsInteractive) {
        this.parentTerminal = parentTerminal;
        if (_commandBuilder == null) throw new RuntimeException(Phrases.getPhrase("CommandBuilderIsNull"));
        commandBuilder = _commandBuilder;
        isInteractive = IsInteractive;
    }

    public ICommandBuilder getCommandBuilder() {
        return commandBuilder;
    }

    @Override
    public void tick() {

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

    @Override
    public void run() {
        try {
            while (!commandBuilder.isDone()) {
                commandBuilder.tick();
                if (commandBuilder.getCommand() != null) {
                    commandBuilder.getCommand().setParentShell(this).run();
                    commandBuilder.reset();
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
