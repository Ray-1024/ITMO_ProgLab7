package ray1024.projects.collectioncontroller.server;

import ray1024.projects.collectioncontroller.general.data.IUser;
import ray1024.projects.collectioncontroller.general.data.MyCollection;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.data.ServerUser;

import java.sql.*;

public class DBController {
    private Connection connection;
    private Statement statement;
    private String login = "postgres";
    private String password = "postgres";
    // Bad Practice
    private String url = "jdbc:postgresql://localhost:5432/studs";

    public DBController() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users( id SERIAL8 PRIMARY KEY, login VARCHAR(30) UNIQUE NOT NULL, salt VARCHAR(10) NOT NULL, password_hash VARCHAR(30))");


        } catch (SQLException e) {
            System.out.println("--- SQL EXCEPTION ---");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("--- CAN'T FIND POSTGRESQL DRIVER ---");
        }
    }

    public synchronized UserManager getUsers() {
        UserManager userManager = new UserManager();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                userManager.addUser(new ServerUser().setLogin(resultSet.getString("login"))
                        .setPassword(resultSet.getString("password_hash")));
            }
        } catch (Throwable ex) {
            //throw new RuntimeException(ex);
        }
        return userManager;
    }

    public synchronized boolean addUser(IUser user) {
        try {

            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    public synchronized MyCollection<StudyGroup> getCollection() {
        return new MyCollection<>();
    }
}
