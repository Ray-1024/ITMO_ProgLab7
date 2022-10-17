package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.data.User;

import java.sql.*;

public class DBController {
    private Connection connection;
    private String login = "postgres";
    private String password = "postgres";
    // Bad Practice
    private String url = "jdbc:postgresql://localhost:5432/studs";

    private final CryptoController cryptoController = new CryptoController();

    public DBController() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users( id BIGSERIAL PRIMARY KEY, login VARCHAR(32) UNIQUE NOT NULL, salt VARCHAR(10) NOT NULL, password_hash VARCHAR(32) NOT NULL)");
        } catch (SQLException e) {
            System.out.println("--- SQL EXCEPTION ---");
        } catch (ClassNotFoundException e) {
            System.out.println("--- CAN'T FIND POSTGRESQL DRIVER ---");
        }
    }

    public synchronized UserManager getUsers() {
        UserManager userManager = new UserManager();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT login,salt,password_hash FROM users");
            while (resultSet.next()) {
                userManager.addUser(new User().setLogin(resultSet.getString("login")).setPassword(resultSet.getString("password_hash")).setSalt(resultSet.getString("salt")));
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            //throw new RuntimeException(ex);
        }
        return userManager;
    }

    public boolean addUser(IUser user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(login, salt, password_hash) VALUES (?,?,?)");
            cryptoController.fixUser(user.setSalt(null));
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getSalt());
            statement.setString(3, user.getPassword());
            statement.execute();
            return true;
        } catch (Throwable ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    public MyCollection<StudyGroup> getCollection() {
        return new MyCollection<>();
    }

    public CryptoController getCryptoController() {
        return cryptoController;
    }
}
