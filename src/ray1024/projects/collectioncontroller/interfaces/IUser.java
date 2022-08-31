package ray1024.projects.collectioncontroller.interfaces;

import ray1024.projects.collectioncontroller.data.User;
import ray1024.projects.collectioncontroller.terminal.Terminal;

import java.net.Socket;

public interface IUser {
    Socket getConnection();

    User setConnection(Socket connection);

    String getLogin();

    IUser setLogin(String login);

    String getPasswordHash();

    IUser setPasswordHash(String passwordHash);

    Terminal getTerminal();

    IUser setTerminal(Terminal terminal);
}
