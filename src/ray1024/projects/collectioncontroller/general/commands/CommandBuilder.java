package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.general.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

import java.io.IOException;

public class CommandBuilder implements Tickable {
    private BaseCommand command;
    private IInputSource reader;
    private IOutputSource writer;

    public CommandBuilder(IInputSource reader, IOutputSource writer) throws IOException {
        if (reader == null) throw new IOException(Phrases.getPhrase("InputSourceIsNull"));
        this.reader = reader;
        if (writer == null) throw new IOException(Phrases.getPhrase("OutputSourceIsNull"));
        this.writer = writer;
        writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
    }

    @Override
    public void tick() throws IOException {
        if (command == null) {
            try {
                if (!reader.hasNextLine()) return;
                command = CommandRegister.getRegisteredCommandByName(reader.nextLine());
                if (command == null) writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
                else if (!command.isObjectReady()) writer.println(command.getStepDescription());
            } catch (IllegalStateException illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        } else if (!command.isObjectReady()) {
            try {
                if (!reader.hasNextLine()) return;
                command.inputLine(reader.nextLine());
                writer.println(command.getStepDescription());
            } catch (IllegalStateException illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        } else writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
    }

    public BaseCommand getCommand() {
        return command == null || !command.isObjectReady() ? null : command;
    }

    public IInputSource getReader() {
        return reader;
    }

    public IOutputSource getWriter() {
        return writer;
    }

    public void reset() {
        command = null;
        writer.println(Phrases.getPhrase("TerminalWaitNewCommand"));
    }
}
