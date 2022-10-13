package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.communication.IConnector;

public class ServerUser implements IUser {
    private String login;
    private String password;
    private IConnector connector;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public ServerUser setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public ServerUser setPassword(String passwordHash) {
        this.password = passwordHash;
        return this;
    }

    @Override
    public IUser setConnector(IConnector connector) {
        this.connector = connector;
        return this;
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IUser b)
            return getLogin().equals(b.getLogin()) && getPassword().equals(b.getPassword());
        return false;
    }
}
