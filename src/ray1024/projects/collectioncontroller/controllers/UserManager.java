package ray1024.projects.collectioncontroller.controllers;

import ray1024.projects.collectioncontroller.interfaces.IUser;
import ray1024.projects.collectioncontroller.interfaces.IUserManager;

import java.util.HashMap;
import java.util.stream.Stream;

public class UserManager implements IUserManager {
    private final HashMap<String, IUser> users = new HashMap<>();

    public UserManager() {
    }

    @Override
    public boolean isRegistred(IUser user) {
        return user != null && users.get(user.getLogin()).getPasswordHash().equals(user.getPasswordHash());
    }

    @Override
    public IUserManager addUser(IUser user) {
        if (user != null && !users.containsKey(user.getLogin()))
            users.put(user.getLogin(), user);
        return this;
    }

    @Override
    public Stream<IUser> stream() {
        return users.values().stream();
    }

}
