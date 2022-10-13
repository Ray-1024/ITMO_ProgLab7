package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;

public class ClientUser implements IUser {

    private String login;
    private String password;

    @Override
    public IUser setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public IUser setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IUser setConnector(IConnector connector) {
        return this;
    }

    @Override
    public IConnector getConnector() {
        return null;
    }
}
