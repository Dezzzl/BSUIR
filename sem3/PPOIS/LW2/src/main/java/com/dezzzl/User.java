package com.dezzzl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class User {
    public static final Class<User> CLASS = User.class;
    private Connection connection;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;

    private String username;

    private String password;

    public void setRole(String role) {
        this.role = role;
    }

    private String role = "USER";

    public String getRole() {
        return role;
    }

    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void addComment(int imageId, int userId, String text) {
        DbManager dbManager = new DbManager();
        dbManager.addComment(imageId, userId, text);
    }

    public void deleteComment(int commentId) {
        DbManager dbManager = new DbManager();
        if (this.getId() == dbManager.getUserIdByCommentId(commentId)) {
            dbManager.deleteComment(commentId);
        }
    }

    public void addRating(int imageId, int userId, int ratingValue) {
        DbManager dbManager = new DbManager();
        dbManager.addRating(imageId, userId, ratingValue);
    }

    public List<Image> searchByTag(int offset, String tag) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getImagesWithTag(offset, tag);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска по тэгу", e);
        }
    }

    public List<Image> searchByAuthor(int offset, String username) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getImageWithAuthor(offset, username);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска по автору", e);
        }
    }

    public List<Image> getAllImages(int offset) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getAllImages(offset);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска всех изображений", e);
        }
    }


}
