package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.interfaces.IInputSource;
import ray1024.projects.collectioncontroller.interfaces.IOutputSource;
import ray1024.projects.collectioncontroller.interfaces.Tickable;
import ray1024.projects.collectioncontroller.tools.Phrases;

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
    }

    @Override
    public void tick() throws IOException {
        if (command == null) {
            try {
                if (!reader.hasNextLine()) return;
                command = CommandRegister.getRegisteredCommandByName(reader.nextLine());
            } catch (Throwable illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        } else if (!command.isObjectReady()) {
            try {
                if (!reader.hasNextLine()) return;
                command.inputLine(reader.nextLine());
            } catch (Throwable illegalStateException) {
                writer.println(illegalStateException.getMessage());
            }
        }
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
    }
}