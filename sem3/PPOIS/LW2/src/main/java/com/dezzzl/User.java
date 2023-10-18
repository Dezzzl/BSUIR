package com.dezzzl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class User {
    public static final Class<User> CLASS = User.class;
    private Connection connection;
    private int id;

    private String email;

    private String username;

    private String password;


    private String role = "USER";

    private static final DbManager dbManager = new DbManager();
    /**
     * Конструктор, создающий пользователя по его id, email, username, password
     * @param id id пользователя
     * @param email email пользователя
     * @param username username пользователя
     * @param password password пользователя
     * */
    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Конструктор, создающий пользователя по его email, username, password
     * @param email email пользователя
     * @param username username пользователя
     * @param password passwors пользователя
     * */
    public User(String email, String username, String password){
        this(0, email, username, password);
    }
    /**
    * Устанавливает id пользователя
    * @param id Id пользователя
    * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Устанавливает email пользователя
     * @param email email пользователя
     * */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Устанавливает username пользователя
     * @param username username пользователя
     * */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Устанавливает password пользователя
     * @param password password пользователя
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Устанавливает role пользователя
     * @param role role пользователя
     * */
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * Возвращает role пользователя
     * @return role пользователя
     * */
    public String getRole() {
        return role;
    }

    /**
     * Возвращает Id пользователя
     * @return Id пользователя
     * */
    public int getId() {
        return id;
    }

    /**
     * Возвращает password пользователя
     * @return роль пользователя
     * */
    public String getPassword() {
        return password;
    }
    /**
     * Возвращает email пользователя
     * @return email пользователя
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Возвращает username пользователя
     * @return username пользователя
     * */
    public String getUsername() {
        return username;
    }

    /**
     * Добавление комментария к изображению
     * @param imageId id изображения под которым пользователь написал комментарий

     * @param text текст комметария
     * */
    public void addComment(int imageId,  String text) {
        dbManager.addComment(imageId, getId(), text);
    }

    /**
     * Удаление комментария под изображением
     * @param commentId id комментария, который нужно удалить
     * */
    public void deleteComment(int commentId) {
        if (this.getId() == dbManager.getUserIdByCommentId(commentId)) {
            dbManager.deleteComment(commentId);
        }
    }
    /**
     * Добавление рейтинга к изображению
     * @param imageId id изображения, к которому пользователь хочет добавить рейтинг
     * @param userId  id пользователя, который хочет добавить рейтинг
     * @param ratingValue численное значение рейтинга
     * */
    public void addRating(int imageId, int userId, int ratingValue) {
        dbManager.addRating(imageId, userId, ratingValue);
    }

    /**
     * Поиск изобразения по тегу
     * @param offset количество записей из базы данных, которое пропуститься
     * @param tag  tag, по которому осуществляется поиск изображения
     * @return список изображений
     * */
    public List<Image> searchByTag(int offset, String tag) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getImagesWithTag(offset, tag);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска по тэгу", e);
        }
    }
    /**
     * Поиск изображения по автору
     * @param offset количество записей из базы данных, которое пропуститься
     * @param username  username автора, по которому осуществляется поиск изображения
     * @return список изображений
     * */
    public List<Image> searchByAuthor(int offset, String username) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getImageWithAuthor(offset, username);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска по автору", e);
        }
    }

    /**
     * Поиск изобразения без фильтрации
     *
     * @param offset количество записей из базы данных, которое пропуститься
     * @return список изображений
     */
    public List<Image> getAllImages(int offset) {
        try (Connection connection = DbManager.open()) {
            SearchEngine searchEngine = new SearchEngine(connection);
            return searchEngine.getAllImages(offset);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска всех изображений", e);
        }
    }


}
