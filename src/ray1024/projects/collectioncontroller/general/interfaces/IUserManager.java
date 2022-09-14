package ray1024.projects.collectioncontroller.general.interfaces;

import java.util.stream.Stream;

public interface IUserManager extends Tickable{
    boolean isRegistred(IUser user);

    IUserManager addUser(IUser user);

    Stream<IUser> stream();
}
