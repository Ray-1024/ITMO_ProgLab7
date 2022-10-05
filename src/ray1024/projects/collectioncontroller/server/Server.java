package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.DBController;
import ray1024.projects.collectioncontroller.general.controllers.IUserManager;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.util.UUID;
import java.util.concurrent.Executor;

public class Server implements Tickable {

    public final IUser admin = new User().setLogin("administrator").setPassword(UUID.randomUUID().toString());
    private final IUserManager usersManager;
    private final Terminal terminal;

    private final ConnectionManager connectionManager;
    private final RequestExecutor requestExecutor;
    private final ResponseSender responseSender;
    private final DBController dbController;


    public ResponseSender getResponseSender() {
        return responseSender;
    }

    public Server() {
        dbController = new DBController();
        usersManager = dbController.getUsers();
        
        terminal = new Terminal(this, new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter()),
                new StudyGroupCollectionController(this, System.getenv("CCFilename")));
        terminal.getCollectionController().loadCollection();

        connectionManager = new ConnectionManager(this);
        requestExecutor = new RequestExecutor(this);
        responseSender = new ResponseSender(this);
    }

    public RequestExecutor getRequestExecutor() {
        return requestExecutor;
    }

    public IUserManager getUsersManager() {
        return usersManager;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    @Override
    public void tick() {
        connectionManager.tick();
        terminal.tick();
    }
}
