package com.dezzzl;

import org.postgresql.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Stream;

public class DbManager {
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
        try (var inputStream = DbManager.class.getClassLoader().getResourceAsStream("application.properties")) {
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

    /**
     * Удаление комментария под изображением
     *
     * @param commentId id комментария, который нужно удалить
     */
    public void deleteComment(int commentId) {
        String query = "DELETE FROM comments WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commentId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Комментарий с ID " + commentId + " успешно удален.");
            } else {
                System.out.println("Комментарий с ID " + commentId + " не найден.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении комментария с ID " + commentId, e);
        }
    }

    /**
     * Добавление комментария к изображению
     *
     * @param imageId     id изображения под которым пользователь написал комментарий
     * @param userId      id пользователя, который добавляет комментарий
     * @param commentText текст комметария
     */
    public void addComment(int imageId, int userId, String commentText) {
        String query = "INSERT INTO comments (image_id, user_id, text, date) VALUES (?, ?, ?, NOW())";

        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);
            statement.setInt(2, userId);
            statement.setString(3, commentText);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении комментария", e);
        }
    }

    /**
     * Удаление изображения по его id
     *
     * @param imageId id изображения, которое нужно удалить
     */
    public void deleteImage(int imageId) {
        String deleteImageQuery = "DELETE FROM images WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(deleteImageQuery)) {
            statement.setInt(1, imageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении изображения: " + e.getMessage(), e);
        }
    }

    /**
     * Удаление пользователя из базы данных
     *
     * @param userId id пользователя, которого нужно удалить
     */
    public void deleteUser(int userId) {
        String deleteUserQuery = "DELETE FROM persons WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(deleteUserQuery)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавление пользователя в базу данных
     *
     * @param user пользователь, которого нужно добавить в базу данных
     */
    public void addUser(User user) {
        String addUserQuery = "INSERT INTO persons (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(addUserQuery)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Добавление тега к изображению
     *
     * @param imageId id изображения, к которому нужно добавить тег
     * @param tagName текст тега
     */
    public void addTagToImage(int imageId, String tagName) {
        int tagId = getTagIdByName(tagName);
        if (tagId != -1) {
            addTagToImageMapping(imageId, tagId);
        } else {
            tagId = createNewTag(tagName);
            addTagToImageMapping(imageId, tagId);
        }
    }

    /**
     * Удаление рейтинга пользователя к изображению
     *
     * @param ratingId id рейтинга
     */
    public void deleteRatingById(int ratingId) {
        String deleteRatingQuery = "DELETE FROM ratings WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(deleteRatingQuery)) {
            statement.setInt(1, ratingId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении рейтинга с ID " + ratingId, e);
        }
    }

    private int getTagIdByName(String tagName) {
        String query = "SELECT id FROM tags WHERE name = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tagName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int createNewTag(String tagName) {
        String createTagQuery = "INSERT INTO tags (name) VALUES (?) RETURNING id";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(createTagQuery)) {
            statement.setString(1, tagName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void addTagToImageMapping(int imageId, int tagId){
        String addMappingQuery = "INSERT INTO image_tags (image_id, tag_id) VALUES (?, ?)";
        try (  Connection connection = open();
        PreparedStatement statement = connection.prepareStatement(addMappingQuery);) {
            statement.setInt(1, imageId);
            statement.setInt(2, tagId);
connection.close();
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление тега из изображения
     *
     * @param imageId id изображения, к из которого удаляется тег
     * @param tagName текст тега
     */
    public void removeTagFromImage(int imageId, String tagName) {
        int tagId = getTagIdByName(tagName);

        if (tagId != -1) {
            String removeMappingQuery = "DELETE FROM image_tags WHERE image_id = ? AND tag_id = ?";
            try (Connection connection = open();
                 PreparedStatement statement = connection.prepareStatement(removeMappingQuery)) {
                statement.setInt(1, imageId);
                statement.setInt(2, tagId);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Добавление тега в базу данных
     *
     * @param tagName текст тега
     * @param tagName текст тега
     */
    public void addTag(String tagName) {
        String query = "INSERT INTO tags (name) VALUES (?)";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tagName);
            statement.executeUpdate();
            System.out.println("Тег \"" + tagName + "\" успешно добавлен.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении тега", e);
        }
    }

    /**
     * Добавление тега к изображению
     *
     * @param userId  id пользователя, у которого меняют роль
     * @param newRole новая роль
     */
    public void changeUserRole(int userId, String newRole) {
        String query = "UPDATE persons SET role = ? WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newRole);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Роль пользователя с ID " + userId + " успешно изменена на \"" + newRole + "\".");
            } else {
                System.out.println("Пользователь с ID " + userId + " не найден.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при изменении роли пользователя", e);
        }
    }

    /**
     * Добавление рейтинга к изображению
     *
     * @param imageId     id изображения, к которому пользователь хочет добавить рейтинг
     * @param userId      id пользователя, который хочет добавить рейтинг
     * @param ratingValue численное значение рейтинга
     */
    public void addRating(int imageId, int userId, int ratingValue) {
        if (checkRatingExists(imageId, userId)) {
            updateRating(imageId, userId, ratingValue);
        } else {
            insertRating(imageId, userId, ratingValue);
        }
    }

    private boolean checkRatingExists(int imageId, int userId) {
        String query = "SELECT * FROM ratings WHERE image_id = ? AND user_id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования рейтинга", e);
        }
    }

    private void insertRating(int imageId, int userId, int ratingValue) {
        String query = "INSERT INTO ratings (image_id, user_id, value) VALUES (?, ?, ?)";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);
            statement.setInt(2, userId);
            statement.setInt(3, ratingValue);
            statement.executeUpdate();
            System.out.println("Рейтинг успешно добавлен.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении рейтинга", e);
        }
    }

    private void updateRating(int imageId, int userId, int ratingValue) {
        String query = "UPDATE ratings SET value = ? WHERE image_id = ? AND user_id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ratingValue);
            statement.setInt(2, imageId);
            statement.setInt(3, userId);
            statement.executeUpdate();
            System.out.println("Рейтинг успешно обновлен.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении рейтинга", e);
        }
    }

    /**
     * Возвращает Id пользователя, который написал комментарий с переданным Id
     *
     * @param commentId id комментария
     * @return Id пользователя, который написал комментарий с переданным Id
     */
    public int getUserIdByCommentId(int commentId) {
        String query = "SELECT user_id FROM comments WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении ID пользователя по ID комментария", e);
        }
    }

    /**
     * Добавление изображения в базу данных
     *
     * @param title       название изображения
     * @param description описание изображения
     * @param userId      Id пользователя
     */
    public void uploadImage(String title, String description, int userId) {
        String query = "INSERT INTO images (title, description, user_id, upload_date) VALUES (?, ?, ?, NOW())";

        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, userId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int imageId = generatedKeys.getInt(1);
                    System.out.println("Изображение успешно добавлено с ID: " + imageId);
                } else {
                    System.out.println("Не удалось получить ID нового изображения.");
                }
            } else {
                System.out.println("Не удалось добавить изображение.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании изображения: " + e.getMessage(), e);
        }
    }

    /**
     * Возвращает Id автора изображения
     *
     * @param imageId id изображения
     * @return Id автора изображения
     */
    public int getUserIdByImageId(int imageId) {
        String query = "SELECT user_id FROM images WHERE id = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении ID пользователя по ID изображения: " + e.getMessage(), e);
        }
        return -1;
    }


    /**
     * Возвращает Id пользователя по его email
     *
     * @param email email пользователя
     * @return Id пользователся с данным email
     */
    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM persons WHERE email = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Возвращает Id пользователя по его username
     *
     * @param username username пользователя
     * @return Id пользователся с данным username
     */
    public int getUserIdByUsername(String username) {
        String query = "SELECT id FROM persons WHERE username = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Возвращает password пользователя по его username
     *
     * @param username username пользователя
     * @return password пользователся с данным username
     */
    public String getPasswordByUsername(String username) {
        String query = "SELECT password FROM persons WHERE username = ?";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пароля пользователя", e);
        }
        return null;
    }

}


