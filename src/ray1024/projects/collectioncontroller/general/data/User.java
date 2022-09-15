package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.RequestType;
import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;

public class User implements IUser {
    private String login;
    private String password;
    private IConnector connection;
    private Terminal terminal;
    private long lastAccessTime;
    private long lastResponseTime;
    private long lastUpdateTime;
    private boolean isActive;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    public User() {
        lastAccessTime = System.currentTimeMillis();
        lastResponseTime = System.currentTimeMillis();
        lastUpdateTime = System.currentTimeMillis();
        isActive = true;
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public IUser setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
        return this;
    }

    @Override
    public IConnector getConnection() {
        return connection;
    }

    @Override
    public User setConnection(IConnector connection) {
        this.connection = connection;
        return this;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String getPasswordHash() {
        return password;
    }

    @Override
    public User setPassword(String passwordHash) {
        this.password = passwordHash;
        return this;
    }

    @Override
    public Terminal getTerminal() {
        return terminal;
    }

    @Override
    public IUser setTerminal(Terminal terminal) {
        this.terminal = terminal;
        return this;
    }

    @Override
    public void tick() {
        try {
            if (!isActive) return;
            if (System.currentTimeMillis() - lastAccessTime > 60 * 1000) {
                isActive = false;
                connection.sendResponse(new Response().setResponseType(ResponseType.DISCONNECT));
                return;
            }
            IRequest request = connection.receiveRequest();

            if (request != null) {
                if (getLogin() != null && getPasswordHash() != null && request.getRequestType() != RequestType.CONNECTION && isActive) {
                    isActive = false;
                    try {
                        connection.sendResponse(new Response().setResponseType(ResponseType.DISCONNECT));
                    } catch (Throwable ignored) {
                    }
                    return;
                }
                System.out.println("---REQUEST---");
                System.out.println(request.getRequestType());
                if (request.getRequestType() == RequestType.EXECUTION_COMMAND)
                    System.out.println(request.getCommand().getName());
                System.out.println("-------------");
                lastAccessTime = System.currentTimeMillis();
                if (request.getRequestType() == RequestType.EXECUTION_COMMAND) {
                    terminal.getMainMicroshell().getCommandBuilder().addCommand(request.getCommand());
                    lastAccessTime = System.currentTimeMillis();
                } else if (request.getRequestType() == RequestType.CONNECTION) {
                    lastAccessTime = System.currentTimeMillis();
                    connection.sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer("RECONNECT"));
                    lastResponseTime = System.currentTimeMillis();
                }
            }
            if (System.currentTimeMillis() - lastResponseTime > 10 * 1000) {
                connection.sendResponse(new Response().setResponseType(ResponseType.I_AM_ALIVE));
                lastResponseTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - lastUpdateTime > 30 * 1000) {
                terminal.getCollectionController().sortManagedCollection();
                connection.sendResponse(new Response().setResponseType(ResponseType.COLLECTION_UPDATE).setCollection(terminal.getCollectionController().getManagedCollection()));
                lastUpdateTime = System.currentTimeMillis();
            }
            terminal.tick();
        } catch (Throwable ignored) {
        }
    }
}
