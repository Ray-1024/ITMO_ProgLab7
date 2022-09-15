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
import java.util.Scanner;

public class Client implements Tickable {

    private final CommandBuilder commandBuilder;
    private IConnector connector;
    private StudyGroupCollectionController collectionController;
    private IUser user;
    private long lastAnswerTime;
    private long lastReConnectTime;

    Client() {
        try {
            collectionController = new StudyGroupCollectionController(null);
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
            lastAnswerTime = System.currentTimeMillis();
            lastReConnectTime = System.currentTimeMillis();
        } catch (Throwable e) {
            for (StackTraceElement i : e.getStackTrace())
                System.out.println(i.toString());
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastAnswerTime > 1000 * 60) {
            System.out.println("---CONNECTION HAS BEEN CLOSED BECAUSE TOO LONG---");
            System.exit(0);
        } else if (System.currentTimeMillis() - lastAnswerTime > 1000 * 10 && System.currentTimeMillis() - lastReConnectTime > 1000) {
            try {
                connector = new ClientConnector(InetAddress.getByName("localhost"), 44147);
                IRequest registrationRequest = new Request().setCommand(null).setUser(user).setRequestType(RequestType.CONNECTION);
                connector.sendRequest(registrationRequest);
                lastReConnectTime = System.currentTimeMillis();
            } catch (Throwable ignored) {

            }
        }
        commandBuilder.tick();
        BaseCommand currCommand = commandBuilder.getCommand();
        if (currCommand != null) {
            if (currCommand.getName().equals(ExitCommand.command.getName())) {
                connector.sendRequest(new Request().setRequestType(RequestType.DISCONNECTION));
                System.exit(0);
            }
            connector.sendRequest(new Request().setCommand(currCommand).setRequestType(RequestType.EXECUTION_COMMAND).setUser(user));
            commandBuilder.reset();
        }
        IResponse response = connector.receiveResponse();
        if (response != null) {
            lastAnswerTime = System.currentTimeMillis();
            /*System.out.println("---RESPONSE---");
            System.out.println(response.getResponseType());
            System.out.println("--------------");*/
            if (response.getResponseType() == ResponseType.ANSWER) {
                commandBuilder.getWriter().println(response.getAnswer());
            } else if (response.getResponseType() == ResponseType.COLLECTION_UPDATE) {
                collectionController.setManagedCollection(response.getCollection());
                //System.out.println("---COLLECTION UPDATED---");
                //System.out.println(collectionController.getManagedCollection().getVec());
                //System.out.println("------------------------");
            } else if (response.getResponseType() == ResponseType.DISCONNECT) {
                commandBuilder.getWriter().println("---CONNECTION HAS BEEN CLOSED---");
                System.exit(0);
            } else if (response.getResponseType() == ResponseType.I_AM_ALIVE) {

            }
        }
    }
}
