package ray1024.projects.collectioncontroller.general.controllers;

import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.tools.Tickable;

import java.util.stream.Stream;

public interface IUserManager extends Tickable {
    boolean isRegistred(IUser user);

    IUserManager addUser(IUser user);

    Stream<IUser> stream();
}
