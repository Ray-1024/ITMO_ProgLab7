package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.client.Connector;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.data.User;
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
            System.out.println("NEW_CONNECTION:" + currConnect.toString());
            tempConnector = new Connector(currConnect);
        }
        if (tempConnector != null) {
            System.out.println("TEMPCONNECTOR_NOT_NULL");
            IRequest request = tempConnector.receiveRequestFromClient();
            if (request != null) {
                System.out.println("REQUEST_NOT_NULL");
                System.out.println(request.getRequestType());
            }
        }
        System.out.println("TERMINAL_TICK");
        serverTerminal.tick();
    }
}
