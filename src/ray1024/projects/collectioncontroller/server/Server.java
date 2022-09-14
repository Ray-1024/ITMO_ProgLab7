package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.client.ClientConnector;
import ray1024.projects.collectioncontroller.general.communication.RequestType;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.interfaces.IConnector;
import ray1024.projects.collectioncontroller.general.interfaces.IRequest;
import ray1024.projects.collectioncontroller.general.interfaces.IUserManager;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.io.IOException;
import java.net.Socket;

public class Server implements Tickable {
    private final ConnectionAcceptor connectionAcceptor;
    private final IUserManager usersManager;
    private Terminal serverTerminal;

    private IConnector tempConnector;

    public Server() {
        connectionAcceptor = new ConnectionAcceptor();
        usersManager = new UserManager();

        try {
            serverTerminal = new Terminal(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter());
            serverTerminal.setCollectionController(new StudyGroupCollectionController(System.getenv("CCFilename")));
            serverTerminal.getCollectionController().loadCollectionFromFile();
        } catch (Exception e) {
            serverTerminal.getWriter().println(e.getMessage());
        }
    }

    @Override
    public void tick() throws IOException {
        Socket currConnect = connectionAcceptor.getNewConnection();
        if (currConnect != null) {
            //usersManager.addUser(new User().setConnection(currConnect));
            System.out.println("NEW_CONNECTION:" + currConnect);
            tempConnector = new ServerConnector(currConnect);
        }
        if (tempConnector != null) {
            IRequest request = tempConnector.receiveRequest();
            if (request != null) {
                System.out.println(request.getRequestType());
                if (request.getRequestType() == RequestType.CONNECTION) {
                    System.out.println("Login: " + request.getUser().getLogin());
                    System.out.println("PasswordHash: " + request.getUser().getPasswordHash());
                }
                if (request.getRequestType() == RequestType.EXECUTION_COMMAND)
                    System.out.println(request.getCommand().getName());
                request = null;
            }
        }
        serverTerminal.tick();
    }
}
