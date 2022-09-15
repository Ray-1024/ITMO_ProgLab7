package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.controllers.IUserManager;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.readers.DevNullReader;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Serializer;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;
import ray1024.projects.collectioncontroller.general.writers.ResponseWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Server implements Tickable {
    private ConnectionAcceptor connectionAcceptor;
    private IUserManager usersManager;
    private Terminal serverTerminal;
    private String usersFilename = "users.bin";

    public Server() {
        try {
            connectionAcceptor = new ConnectionAcceptor();
            loadUsers(usersFilename);
            serverTerminal = new Terminal(new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter()));
            serverTerminal.setCollectionController(new StudyGroupCollectionController(System.getenv("CCFilename")));
            serverTerminal.getCollectionController().loadCollectionFromFile();
        } catch (Exception e) {
            serverTerminal.getWriter().println(e.getMessage());
        }
    }

    private void loadUsers(String filename) {
        try {
            usersManager = (IUserManager) Serializer.deserialize(Files.readAllBytes(Paths.get(filename)));
        } catch (Throwable ex) {
            usersManager = new UserManager();
        }
    }

    public void saveUsers(String filename) {
        try {
            Files.write(Paths.get(filename), Serializer.serialize(usersManager));
        } catch (Throwable ignored) {
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
                Terminal userTerminal = new Terminal(new CommandBuilder(new DevNullReader(), new ResponseWriter(currConnect))).setCollectionController(serverTerminal.getCollectionController());
                IUser user = new User();
                userTerminal.setMaster(user);
                usersManager.addUser(user.setConnection(currConnect).setLastAccessTime(System.currentTimeMillis()).setTerminal(userTerminal));
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                saveUsers(usersFilename);
                System.exit(0);
            }
            currConnect = connectionAcceptor.getNewConnection();
        }
        usersManager.tick();
        serverTerminal.tick();
    }
}
