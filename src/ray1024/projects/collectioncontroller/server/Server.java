package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.interfaces.IConnector;
import ray1024.projects.collectioncontroller.general.interfaces.IUserManager;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.readers.DevNullReader;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.general.writers.DevNullWriter;
import ray1024.projects.collectioncontroller.general.writers.ResponseWriter;

import java.io.IOException;

public class Server implements Tickable {
    private ConnectionAcceptor connectionAcceptor;
    private IUserManager usersManager;
    private Terminal serverTerminal;

    public Server() {
        try {
            connectionAcceptor = new ConnectionAcceptor();
            usersManager = new UserManager();
            serverTerminal = new Terminal(new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter()));
            serverTerminal.setCollectionController(new StudyGroupCollectionController(System.getenv("CCFilename")));
            serverTerminal.getCollectionController().loadCollectionFromFile();
        } catch (Exception e) {
            serverTerminal.getWriter().println(e.getMessage());
        }
    }

    public IUserManager getUsersManager() {
        return usersManager;
    }

    public Terminal getServerTerminal() {
        return serverTerminal;
    }

    @Override
    public void tick() {
        IConnector currConnect = connectionAcceptor.getNewConnection();
        while (currConnect != null) {
            try {
                System.out.println("---NEW CONNECTION---");
                Terminal userTerminal = new Terminal(
                        new CommandBuilder(
                                new DevNullReader(),
                                new ResponseWriter(currConnect)))
                        .setCollectionController(serverTerminal.getCollectionController());
                usersManager.addUser(new User()
                        .setConnection(currConnect)
                        .setLastAccessTime(System.currentTimeMillis())
                        .setTerminal(userTerminal));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            currConnect = connectionAcceptor.getNewConnection();
        }
        usersManager.tick();
        serverTerminal.tick();
    }
}
