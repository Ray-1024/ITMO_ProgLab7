package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.util.HashMap;
import java.util.stream.Stream;

public class UserManager implements IUserManager {
    private final HashMap<String, IUser> users;

    public UserManager() {
        users = new HashMap<>();
    }

    @Override
    public boolean isRegistered(IUser user) {
        return user.getLogin() != null && users.containsKey(user.getLogin());
    }

    @Override
    public synchronized IUserManager addUser(IUser user) {
        if (user == null) return this;
        if (!isRegistered(user)) users.put(user.getLogin(), user);
        return this;
    }

    @Override
    public IUser getUser(String login) {
        return users.get(login);
    }

    @Override
    public Stream<IUser> stream() {
        return users.values().stream();
    }

}
