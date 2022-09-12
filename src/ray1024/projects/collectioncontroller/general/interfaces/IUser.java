package ray1024.projects.collectioncontroller.general.interfaces;

import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.general.terminal.Terminal;

import java.net.Socket;

public interface IUser {
    Socket getConnection();

    User setConnection(Socket connection);

    String getLogin();

    IUser setLogin(String login);

    String getPasswordHash();

    IUser setPassword(String password);

    Terminal getTerminal();

    IUser setTerminal(Terminal terminal);
}
