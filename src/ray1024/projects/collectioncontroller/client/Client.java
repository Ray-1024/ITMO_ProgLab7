package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.communication.RequestType;
import ray1024.projects.collectioncontroller.general.communication.Request;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.interfaces.*;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Client implements Tickable {

    private final CommandBuilder commandBuilder;
    private IConnector connector;
    private IUser user;

    Client() {
        try {
            user = new User();
            Scanner scanner = new Scanner(System.in);
            System.out.println(Phrases.getPhrase("PleaseEnterLogin"));
            if (scanner.hasNextLine()) user.setLogin(scanner.nextLine());
            System.out.println(Phrases.getPhrase("PleaseEnterPassword"));
            if (scanner.hasNextLine()) user.setPassword(scanner.nextLine());
            commandBuilder = new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
            connector = new ClientConnector(InetAddress.getByName("localhost"), 44147);
            IRequest registrationRequest = new Request().setCommand(null).setUser(user).setRequestType(RequestType.CONNECTION);
            connector.sendRequest(registrationRequest);
        } catch (Throwable e) {
            for (StackTraceElement i : e.getStackTrace())
                System.out.println(i.toString());
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tick() throws IOException {
        commandBuilder.tick();
        BaseCommand currCommand = commandBuilder.getCommand();
        if (currCommand != null) {
            IRequest request = new Request().setCommand(currCommand).setRequestType(RequestType.EXECUTION_COMMAND);
            connector.sendRequest(request);
            commandBuilder.reset();
        }
        IResponse response = connector.receiveResponse();
    }
}
