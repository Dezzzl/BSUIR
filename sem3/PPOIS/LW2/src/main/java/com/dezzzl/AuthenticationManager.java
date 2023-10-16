package com.dezzzl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationManager {
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

    public boolean loginUser(String username, String password) {

        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByUsername(username) == -1) {
            return false;
        } else if (!Objects.equals(dbManager.getPasswordByUsername(username), password)) {
            return false;
        } else {

            // Проверка введенных данных в базе данных
            // Ваш код для проверки данных в базе данных
            // Если пользователь существует и введенный пароль совпадает, вернуть true, иначе false

            System.out.println("Пользователь успешно вошел в систему.");
            return true; // Возвращаем true, если пользователь успешно вошел
        }
    }

}
