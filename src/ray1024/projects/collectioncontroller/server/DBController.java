package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.User;
import ray1024.projects.collectioncontroller.server.UserManager;

import java.sql.*;

public class DBController {
    private Connection connection;
    private Statement statement;
    private String login = "postgres";
    private String password = "zh159sm212140";
    // Bad Practice
    private String url = "jdbc:postgresql://localhost:5432/studs";

    public DBController() {
        try {
            connection = DriverManager.getConnection(url, login, password);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users" +
                    "(" +
                    "    id            SERIAL8 PRIMARY KEY," +
                    "    login         VARCHAR(30) UNIQUE NOT NULL," +
                    "    salt          VARCHAR(10)        NOT NULL," +
                    "    password_hash VARCHAR(30)" +
                    ")");

        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }

    public UserManager getUsers() {
        UserManager userManager = new UserManager();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                IUser curr = new User();
                curr.setLogin(resultSet.getString("login"))
                        .setPassword(resultSet.getString(""));
            }
        } catch (Throwable ex) {
            //throw new RuntimeException(ex);
        }
        return userManager;
    }
}
