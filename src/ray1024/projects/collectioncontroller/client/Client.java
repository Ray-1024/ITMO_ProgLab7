package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.communicationtypes.RequestType;
import ray1024.projects.collectioncontroller.general.data.Request;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.interfaces.IRequest;
import ray1024.projects.collectioncontroller.general.interfaces.IUser;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

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
            IRequest registrationRequest = new Request().setCommand(null).setUser(user).setRequestType(RequestType.REGISTRATION);
            System.out.println("<COOKING_REGISTRATION_REQUEST>");
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
            System.out.println("<COOKING_REQUEST(NOT YET)>");
        }
    }
}
