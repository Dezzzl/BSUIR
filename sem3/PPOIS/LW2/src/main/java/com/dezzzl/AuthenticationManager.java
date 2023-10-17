package com.dezzzl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationManager {
    /**
     * Возвращает true, если пользователю удалось зарегистрироваться, false, если не удалось
     *
     * @param username username пользователя
     * @param password password пользователя
     * @param email    email пользователя
     * @return true, если пользователю удалось зарегистрироваться, false - если не удалось
     */
    public boolean registerUser(String username, String password, String email) {
        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByEmail(email) != -1) {
            return false;
        } else if (dbManager.getUserIdByUsername(username) != -1) {
            return false;
        } else {
            dbManager.addUser(new User(email, username, password));
        }
        System.out.println("Пользователь успешно зарегистрирован.");
        return true;
    }

    /**
     * Возвращает true, если пользователю удалось войти, false, если не удалось
     *
     * @param username username пользователя
     * @param password password пользователя
     * @return true, если пользователю удалось войти, false, если не удалось
     */
    public boolean loginUser(String username, String password) {

        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByUsername(username) == -1) {
            return false;
        } else if (!Objects.equals(dbManager.getPasswordByUsername(username), password)) {
            return false;
        } else {
            System.out.println("Пользователь успешно вошел в систему.");
            return true;
        }
    }

}
