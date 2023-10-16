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

    private static void loadProperties() {
        try (var inputStream = DbManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String get(String key) {
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

    public void createImage(String title, String description, int userId) {
        String query = "INSERT INTO images (title, description, user_id) VALUES (?, ?, ?)";

        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, userId);

            statement.executeUpdate();

            System.out.println("Изображение успешно добавлено в базу данных.");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании изображения: " + e.getMessage(), e);
        }
    }

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



    public void addTagToImage(int imageId, String tagName) {
        // Проверяем, существует ли тег с указанным именем
        int tagId = getTagIdByName(tagName);

        // Если тег существует, связываем его с картинкой
        if (tagId != -1) {
            addTagToImageMapping(imageId, tagId);
        } else {
            // Если тег не существует, создаем новый тег и связываем его с картинкой
            tagId = createNewTag(tagName);
            addTagToImageMapping(imageId, tagId);
        }
    }


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
            e.printStackTrace(); // обработка ошибок
        }
        // Если тег с указанным именем не найден, возвращаем -1
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
            e.printStackTrace(); // обработка ошибок
        }
        // Если не удалось создать новый тег, возвращаем -1
        return -1;
    }

    private void addTagToImageMapping(int imageId, int tagId) {
        String addMappingQuery = "INSERT INTO image_tags (image_id, tag_id) VALUES (?, ?)";
        try (Connection connection = open();
             PreparedStatement statement = connection.prepareStatement(addMappingQuery)) {
            statement.setInt(1, imageId);
            statement.setInt(2, tagId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок
        }
    }

    public void removeTagFromImage(int imageId, String tagName) {
        int tagId = getTagIdByName(tagName);

        if (tagId != -1) {
            // Удаляем связь тега с картинкой
            String removeMappingQuery = "DELETE FROM image_tags WHERE image_id = ? AND tag_id = ?";
            try (Connection connection = open();
                 PreparedStatement statement = connection.prepareStatement(removeMappingQuery)) {
                statement.setInt(1, imageId);
                statement.setInt(2, tagId);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // обработка ошибок
            }
        }
    }

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

    public void addRating(int imageId, int userId, int ratingValue) {
        // Проверяем, существует ли рейтинг от данного пользователя для данного изображения
        if (checkRatingExists(imageId, userId)) {
            // Если рейтинг уже существует, обновляем его значение
            updateRating(imageId, userId, ratingValue);
        } else {
            // Если рейтинг не существует, добавляем новый
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
        // Если изображение с указанным ID не найдено, возвращаем -1 или другое значение по умолчанию
        return -1;
    }

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
            e.printStackTrace(); // обработка ошибок
        }
        return -1; // Возвращаем -1, если пользователь с указанным email не найден
    }

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
            e.printStackTrace(); // обработка ошибок
        }
        return -1; // Возвращаем -1, если пользователь с указанным username не найден
    }

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


