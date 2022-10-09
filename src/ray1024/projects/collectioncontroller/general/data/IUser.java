package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;

import java.io.Serializable;

public interface IUser extends Serializable {

    IUser setLogin(String login);

    IUser setPassword(String password);

    String getLogin();

    String getPassword();

    IUser setConnector(IConnector connector);

    IConnector getConnector();

}
