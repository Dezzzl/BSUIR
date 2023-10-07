package com.dezzzl;

import org.postgresql.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Stream;

public class DbManager {
    private static final String PASSWORD_KEY = "db.password";
    public static final String URL_KEY = "db.url";

    public static final String USERNAME_KEY = "db.username";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = DbManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    get(URL_KEY),
                    get(USERNAME_KEY),
                    get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = open()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }

}
