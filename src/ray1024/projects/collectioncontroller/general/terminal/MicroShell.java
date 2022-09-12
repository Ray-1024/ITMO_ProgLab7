package ray1024.projects.collectioncontroller.general.terminal;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;

/**
 * Класс предназначенный для исполнения команд из очереди, пока та не закончится
 */
public class MicroShell implements Tickable {
    private final Terminal parentTerminal;
    private CommandBuilder commandBuilder;
    private boolean isInteractive;
    private boolean isDone = false;

    public MicroShell(Terminal parentTerminal, CommandBuilder _commandBuilder, boolean IsInteractive) {
        this.parentTerminal = parentTerminal;
        if (_commandBuilder == null) throw new RuntimeException(Phrases.getPhrase(""));
        commandBuilder = _commandBuilder;
        isInteractive = IsInteractive;
    }

    @Override
    public void tick() {
        if (isDone) return;
        try {
            commandBuilder.tick();
        } catch (IOException e) {
            isDone = true;
        }
        if (commandBuilder.getCommand() != null) {
            commandBuilder.getCommand().setParentShell(this).execute();
            commandBuilder.reset();
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
        return isDone;
    }
}
