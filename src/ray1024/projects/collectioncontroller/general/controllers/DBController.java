package ray1024.projects.collectioncontroller.general.controllers;

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
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users\n" +
                    "(\n" +
                    "    id            SERIAL8 PRIMARY KEY,\n" +
                    "    login         VARCHAR(30) UNIQUE NOT NULL,\n" +
                    "    salt          VARCHAR(10)        NOT NULL,\n" +
                    "    password_hash VARCHAR(30)\n" +
                    ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserManager getUsers() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            return new UserManager();
        } catch (Throwable ex) {
            return new UserManager();
        }
    }
}
