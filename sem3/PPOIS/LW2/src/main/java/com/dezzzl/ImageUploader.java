package com.dezzzl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {
    private Connection connection;

    public ImageUploader(Connection connection) {
        this.connection = connection;
    }

//    public void uploadImage(Image image) {
//        String insertImageQuery = "INSERT INTO images (title, description, user_id, upload_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
//
//        try (PreparedStatement imageStatement = connection.prepareStatement(insertImageQuery)) {
//            imageStatement.setString(1, image.getTitle());
//            imageStatement.setString(2, image.getDescription());
//            imageStatement.setInt(3, image.getPersonId()); // Предполагаем, что у Image есть метод getUserId() для получения ID пользователя
//            imageStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Обработка ошибок
//        }
//    }
}
