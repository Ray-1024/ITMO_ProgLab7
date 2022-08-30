package ray1024.projects.collectioncontroller.data;

import ray1024.projects.collectioncontroller.interfaces.IUser;
import ray1024.projects.collectioncontroller.interfaces.IUserManager;
import ray1024.projects.collectioncontroller.terminal.Terminal;

import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable, IUser {
    private String login;
    private String passwordHash;
    private Socket connection;
    private Terminal terminal;

    @Override
    public Socket getConnection() {
        return connection;
    }

    @Override
    public User setConnection(Socket connection) {
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
        return passwordHash;
    }

    @Override
    public User setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public IUser setTerminal(Terminal terminal) {
        this.terminal = terminal;
        return this;
    }
}
