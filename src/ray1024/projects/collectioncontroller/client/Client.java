package ray1024.projects.collectioncontroller.client;

import ray1024.projects.collectioncontroller.general.commands.BaseCommand;
import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.commands.ExitCommand;
import ray1024.projects.collectioncontroller.general.communication.*;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.tools.Phrases;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Tickable {

    private final CommandBuilder commandBuilder;
    private final IConnector connector;
    private final StudyGroupCollectionController collectionController;
    private final IUser user;

    Client() {
        try {
            collectionController = new StudyGroupCollectionController(null, null);
            user = new User();

            Scanner scanner = new Scanner(System.in);
            System.out.println(Phrases.getPhrase("PleaseEnterLogin"));
            if (scanner.hasNextLine()) user.setLogin(scanner.nextLine());
            System.out.println(Phrases.getPhrase("PleaseEnterPassword"));
            if (scanner.hasNextLine()) user.setPassword(scanner.nextLine());

            commandBuilder = new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
            connector = new ClientConnector(InetAddress.getByName("localhost"), 44147);
            connector.sendRequest(new Request().setUser(user).setRequestType(RequestType.REGISTRATION));
        } catch (Throwable e) {
            System.out.println("SERVER DOESN'T EXIST");
            try {
                throw e;
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    @Override
    public void tick() {
        commandBuilder.tick();
        BaseCommand currCommand = commandBuilder.getCommand();
        if (currCommand != null) {
            if (currCommand.getName().equals(ExitCommand.command.getName())) {
                connector.sendRequest(new Request().setRequestType(RequestType.DISCONNECTION));
                System.exit(0);
            }
            System.out.println("--- REQUEST SENT ---");
            connector.sendRequest(new Request().setCommand(currCommand).setRequestType(RequestType.EXECUTION_COMMAND).setUser(user));
            commandBuilder.reset();
        }
        IResponse response = connector.receiveResponse();
        if (response != null) {
            System.out.println("--- RESPONSE ---");
            if (response.getResponseType() == ResponseType.ANSWER) {
                commandBuilder.getWriter().println(response.getAnswer());
            } else if (response.getResponseType() == ResponseType.COLLECTION_UPDATE) {
                collectionController.setManagedCollection(response.getCollection());
            } else if (response.getResponseType() == ResponseType.DISCONNECT) {
                commandBuilder.getWriter().println("---CONNECTION HAS BEEN CLOSED---");
                System.exit(0);
            }
        }
    }
}
