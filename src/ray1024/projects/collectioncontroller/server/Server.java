package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.controllers.UserManager;
import ray1024.projects.collectioncontroller.data.User;
import ray1024.projects.collectioncontroller.interfaces.IUserManager;
import ray1024.projects.collectioncontroller.interfaces.Tickable;
import ray1024.projects.collectioncontroller.terminal.Terminal;
import ray1024.projects.collectioncontroller.writers.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.readers.NonBlockingConsoleSourceReader;

import java.io.IOException;
import java.net.Socket;

public class Server implements Tickable {
    private final ConnectionAcceptor connectionAcceptor;
    private final IUserManager usersManager;
    private Terminal serverTerminal;

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
        usersManager.addUser(new User().setConnection(currConnect));
        serverTerminal.tick();
    }
}
