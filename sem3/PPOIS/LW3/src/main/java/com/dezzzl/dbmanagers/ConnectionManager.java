package com.dezzzl.dbmanagers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final String PASSWORD_KEY = "db.password";
    public static final String URL_KEY = "db.url";
    public static final String USERNAME_KEY = "db.username";
    private static final Properties PROPERTIES = new Properties();



    static {
        loadProperties();
    }

    /**
     * Загружает свойства базы данных из файла application.properties
     */
    private static void loadProperties() {
        try (var inputStream = ConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает одно из свойств базы данных по переданному ключ
     *
     * @param key id ключ для одного из свойств базы данных
     * @return одно из свойств базы данных по переданному ключ
     */
    private static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * возвращает Connection с базой данных
     *
     * @return Connection с базой данных
     */
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
}
