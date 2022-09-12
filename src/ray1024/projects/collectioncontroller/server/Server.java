package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.data.User;
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
        if (currConnect != null) usersManager.addUser(new User().setConnection(currConnect));
        serverTerminal.tick();
    }
}
