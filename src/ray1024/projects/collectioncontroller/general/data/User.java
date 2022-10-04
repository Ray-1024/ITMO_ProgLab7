package ray1024.projects.collectioncontroller.general.data;

public class User implements IUser {
    private String login;
    private String password;

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public User setPassword(String passwordHash) {
        this.password = passwordHash;
        return this;
    }
}
