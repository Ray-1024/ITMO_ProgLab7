package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.interfaces.IUser;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable, IUser, Tickable {
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
    public User setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
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
    public void tick() throws IOException {

    }
}
