package ray1024.projects.collectioncontroller.general.controllers;

import ray1024.projects.collectioncontroller.general.data.IUser;

import java.util.stream.Stream;

public interface IUserManager {
    boolean isRegistered(String login);

    IUserManager addUser(IUser user);

    IUser getUser(String login);

    Stream<IUser> stream();
}
