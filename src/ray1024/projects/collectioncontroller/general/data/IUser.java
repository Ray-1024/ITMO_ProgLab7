package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;

public interface IUser {

    IUser setLogin(String login);

    IUser setPassword(String password);

    String getLogin();

    String getPassword();

    IUser setConnector(IConnector connector);

    IConnector getConnector();

}
