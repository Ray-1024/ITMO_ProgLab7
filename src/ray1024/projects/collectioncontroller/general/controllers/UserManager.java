package ray1024.projects.collectioncontroller.general.controllers;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.interfaces.IUser;
import ray1024.projects.collectioncontroller.general.interfaces.IUserManager;
import ray1024.projects.collectioncontroller.general.interfaces.Tickable;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class UserManager implements IUserManager, Tickable, Serializable {
    private final HashMap<String, IUser> users = new HashMap<>();
    private final List<IUser> unknowns = new LinkedList<>();

    public UserManager() {
    }

    @Override
    public boolean isRegistred(IUser user) {
        return user != null && users.get(user.getLogin()) != null && users.get(user.getLogin()).getPasswordHash().equals(user.getPasswordHash());
    }

    @Override
    public IUserManager addUser(IUser user) {
        if (user == null) return this;
        if ((user.getLogin() == null || user.getPasswordHash() == null) && user.getConnection() != null)
            unknowns.add(user);
        else if (!users.containsKey(user.getLogin()) && user.getPasswordHash() != null)
            users.put(user.getLogin(), user);
        else {
            try {
                user.getConnection().sendResponse(new Response().setResponseType(ResponseType.DISCONNECT));
            } catch (Throwable ignored) {
            }
        }
        return this;
    }

    @Override
    public Stream<IUser> stream() {
        return users.values().stream();
    }

    @Override
    public void tick() {
        List<IUser> forDel = new LinkedList<>();
        for (IUser user : unknowns) {
            user.tick();
            if (user.getLogin() != null && user.getPasswordHash() != null && !users.containsKey(user.getLogin()) && user.getPasswordHash() != null) {
                users.put(user.getLogin(), user);
                forDel.add(user);
            }
        }
        unknowns.removeAll(forDel);
        for (IUser user : users.values()) {
            user.tick();
        }
    }
}
