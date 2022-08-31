package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.commands.BaseCommand;
import ray1024.projects.collectioncontroller.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.data.Request;
import ray1024.projects.collectioncontroller.data.User;
import ray1024.projects.collectioncontroller.enums.RequestType;
import ray1024.projects.collectioncontroller.interfaces.IRequest;
import ray1024.projects.collectioncontroller.interfaces.IUser;
import ray1024.projects.collectioncontroller.interfaces.Tickable;
import ray1024.projects.collectioncontroller.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.tools.Phrases;
import ray1024.projects.collectioncontroller.writers.ConsoleSourceWriter;

import java.io.IOException;
import java.net.Socket;

public class Client implements Tickable {

    private final CommandBuilder commandBuilder;
    private Socket socket;
    private IUser user;

    Client() {
        try {
            commandBuilder = new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
            socket = new Socket("localhost", 44147);
            user = new User();

        } catch (IOException e) {
            throw new RuntimeException(Phrases.getPhrase("ClientCan'tStart"));
        }

    }

    @Override
    public void tick() throws IOException {
        commandBuilder.tick();
        BaseCommand currCommand = commandBuilder.getCommand();
        if (currCommand != null) {
            IRequest request = new Request().setCommand(currCommand).setRequestType(RequestType.EXECUTION_COMMAND);
        }
    }
}
