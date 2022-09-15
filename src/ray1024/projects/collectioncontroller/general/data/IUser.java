package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.io.Serializable;

public interface IUser extends Tickable, Serializable {
    IConnector getConnection();

    User setConnection(IConnector connection);

    String getLogin();

    IUser setLogin(String login);

    String getPasswordHash();

    IUser setPassword(String password);

    Terminal getTerminal();

    IUser setTerminal(Terminal terminal);

    IUser setLastAccessTime(long lastAccessTime);

    long getLastAccessTime();
}
