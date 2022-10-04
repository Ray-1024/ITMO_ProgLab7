package ray1024.projects.collectioncontroller.general.controllers;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.util.HashMap;
import java.util.stream.Stream;

public class UserManager implements IUserManager {
    private final HashMap<String, IUser> users;

    public UserManager() {
        users = new HashMap<>();
    }

    @Override
    public boolean isRegistered(String login) {
        return login != null && users.containsKey(login);
    }

    @Override
    public IUserManager addUser(IUser user) {

        return this;
    }

    @Override
    public IUser getUser(String login) {
        return isRegistered(login) ? users.get(login) : null;
    }

    @Override
    public Stream<IUser> stream() {
        return users.values().stream();
    }

}
