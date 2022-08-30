package ray1024.projects.collectioncontroller.interfaces;

import java.util.stream.Stream;

public interface IUserManager {
    boolean isRegistred(IUser user);

    IUserManager addUser(IUser user);

    Stream<IUser> stream();
}
