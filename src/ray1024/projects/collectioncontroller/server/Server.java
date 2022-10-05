package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.commands.CommandBuilder;
import ray1024.projects.collectioncontroller.general.controllers.IUserManager;
import ray1024.projects.collectioncontroller.general.controllers.StudyGroupCollectionController;
import ray1024.projects.collectioncontroller.general.controllers.UserManager;
import ray1024.projects.collectioncontroller.general.readers.NonBlockingConsoleSourceReader;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Tickable;
import ray1024.projects.collectioncontroller.general.writers.ConsoleSourceWriter;

import java.util.concurrent.Executor;

public class Server implements Tickable {
    private final IUserManager usersManager;
    private final Terminal terminal;

    private final ConnectionManager connectionManager;
    private final RequestExecutor requestExecutor;
    private final ResponseSender responseSender;


    public ResponseSender getResponseSender() {
        return responseSender;
    }

    public Server() {

        terminal = new Terminal(new CommandBuilder(new NonBlockingConsoleSourceReader(), new ConsoleSourceWriter()),
                new StudyGroupCollectionController(this, System.getenv("CCFilename")));
        terminal.getCollectionController().loadCollection();
        usersManager = new UserManager();
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

    public Executor getExecutor() {
        return terminal;
    }

    @Override
    public void tick() {
        terminal.tick();
        connectionManager.tick();
    }
}
