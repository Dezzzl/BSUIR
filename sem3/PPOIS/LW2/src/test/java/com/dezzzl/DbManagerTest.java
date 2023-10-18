package com.dezzzl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class DbManagerTest {

   private String username = "TestUsername";
    private String email = "test@gmail.com";
    private String password = "TestPassword";
    private String role = "USER";

    private String tittle = "TestTittle";

    private String description = "TestDescription";

    private Admin admin =new Admin(email, username, password);

private String text = "TestText";

    private final DbManager dbManager = new DbManager();

    @Test
    public void testCreateImage() throws SQLException {
        String title = "Test Image";
        String description = "Description";
        int userId = 1;
        dbManager.uploadImage(title, description, userId);
        try (Connection connection = DbManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM images WHERE title = '" + title + "'")) {
            assertTrue(resultSet.next());
            assertEquals(title, resultSet.getString("title"));
            assertEquals(description, resultSet.getString("description"));
            assertEquals(userId, resultSet.getInt("user_id"));
            int imageId = resultSet.getInt("id");
            admin.deleteImage(imageId);
        }
    }

    @Test
    public void testDeleteImage() throws SQLException {
        String title = "Test Image";
        String description = "Description";
        int userId = 1;
        dbManager.uploadImage(title, description, userId);
        int imageId;
        try (Connection connection = DbManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM images WHERE title = '" + title + "'")) {
            assertTrue(resultSet.next());
            imageId = resultSet.getInt("id");
        }
        dbManager.deleteImage(imageId);
        try (Connection connection = DbManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM images WHERE id = " + imageId)) {
            assertFalse(resultSet.next());
        }
    }

    @Test
    public void testAddUser() {
        String username = "TestUsername";
        String email = "test@gmail.com";
        String password = "TestPassword";
        User user = new User(email, username, password);
        dbManager.addUser(user);
        int userId = dbManager.getUserIdByUsername(username);
        assertNotEquals(-1, userId);
        admin.deleteUser(userId);
        int deletedUserId = dbManager.getUserIdByUsername(username);
        assertEquals(-1, deletedUserId);
    }

    @Test
    public void testDeleteUser() {
        String addUserQuery = "INSERT INTO persons (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbManager.open();
             PreparedStatement statement = connection.prepareStatement(addUserQuery)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int userId = dbManager.getUserIdByUsername(username);
        assertNotEquals(-1, userId);
        dbManager.deleteUser(userId);
        int deletedUserId = dbManager.getUserIdByUsername(username);
        assertEquals(-1, deletedUserId);
    }

    @Test
    public void testRatingActions() throws SQLException {
        int imageId = 2;
        int userId = 2;
        int value = 4;
        dbManager.addRating(imageId, userId, value);
        String maxIdQuery="SELECT MAX(id) FROM ratings";
        int id=-1;
        try(Connection connection = DbManager.open();
        PreparedStatement statement = connection.prepareStatement(maxIdQuery);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        }
        assertNotEquals(-1, id);
        value = 2;
        dbManager.addRating(imageId, userId, value);
        int retrievedImageId = -1;
        int retrievedUserId = -1;
        int retrievedValue = -1;
        String selectQuery= "SELECT image_id, user_id, value FROM ratings WHERE id = ?";
        try(Connection connection = DbManager.open();
            PreparedStatement statement = connection.prepareStatement(selectQuery);) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    retrievedImageId = resultSet.getInt("image_id");
                    retrievedUserId = resultSet.getInt("user_id");
                    retrievedValue = resultSet.getInt("value");
                }
            }
        }
        assertEquals(imageId, retrievedImageId);
        assertEquals(userId, retrievedUserId);
        assertEquals(value, retrievedValue);
        dbManager.deleteRatingById(id);
        int count;
        String countQuery = "SELECT COUNT(*) FROM ratings WHERE id = ?";
        try (Connection connection = DbManager.open();
             PreparedStatement statement = connection.prepareStatement(countQuery)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                } else {
                    count = 0;
                }
            }
        }

        assertEquals(0, count);
    }

    @Test
    public void testTagActions() throws SQLException {
        String[] tagNames = {"TestTag",  "TestTagNew"};
        int[] tagIds = new int[2];
        dbManager.addTag(tagNames[0]);
        admin.addTag(4, tagNames[1]);
        for (int i = 0; i < tagNames.length; i++) {
            String selectQuery = "SELECT id FROM tags WHERE name = ?;";
            try (Connection connection = DbManager.open();
                 PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setString(1, tagNames[i]);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        tagIds[i] = resultSet.getInt(1);
                    } else {
                        tagIds[i] = -1;
                    }
                }
            }
            assertNotEquals(-1, tagIds[i]);
        }

        for (String tagName : tagNames) {
            admin.removeTag(4, tagName);
            String deleteQuery = "DELETE FROM tags WHERE name = ?";

            String selectQuery = "SELECT id FROM tags WHERE name = ?;";
            try (Connection connection = DbManager.open();
                 PreparedStatement statement1 = connection.prepareStatement(selectQuery);
            PreparedStatement statement2 = connection.prepareStatement(deleteQuery);) {
                statement1.setString(1, tagName);
                statement2.setString(1, tagName);
                statement2.executeUpdate();
                try (ResultSet resultSet = statement1.executeQuery()) {
                    if (resultSet.next()) {
                        int tagId = resultSet.getInt(1);
                        assertEquals(-1, tagId);
                    }
                }
            }
        }
    }

    @Test
    public void testGetPasswordByUsername() {
        User user = new User(email,username, password);
        dbManager.addUser(user);
       String realPassword = dbManager.getPasswordByUsername(username);
       assertEquals(password, realPassword);
      int userId =  dbManager.getUserIdByEmail(email);
       dbManager.deleteUser(userId);
    }

    @Test
    public void testGetUserIdByImageId() throws SQLException {

        User user = new User(email,username, password);
        dbManager.addUser(user);
        int userId =  dbManager.getUserIdByEmail(email);
        dbManager.uploadImage(tittle, description, userId);
        String maxIdQuery="SELECT MAX(id) FROM images";
        int imageId=-1;
        try(Connection connection = DbManager.open();
            PreparedStatement statement = connection.prepareStatement(maxIdQuery);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    imageId = resultSet.getInt(1);
                }
            }
        }
       int realUserId = dbManager.getUserIdByImageId(imageId);
        assertEquals(userId, realUserId);
        dbManager.deleteUser(userId);
    }

    @Test
    public void commentActions() throws SQLException {
        User user = new User(email,username, password);
        dbManager.addUser(user);
        int userId =  dbManager.getUserIdByEmail(email);
        user.setId(userId);
        dbManager.uploadImage(tittle, description, userId);
        String maxImageIdQuery="SELECT MAX(id) FROM images";
        int imageId=-1;
        try(Connection connection = DbManager.open();
            PreparedStatement statement = connection.prepareStatement(maxImageIdQuery);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    imageId = resultSet.getInt(1);
                }
            }
        }

        user.addComment(imageId, text);
        String maxCommentIdQuery="SELECT MAX(id) FROM comments";
        int commentId=-1;
        try(Connection connection = DbManager.open();
            PreparedStatement statement = connection.prepareStatement(maxCommentIdQuery);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    commentId = resultSet.getInt(1);
                }
            }
        }
        assertNotEquals(-1, commentId);
        user.deleteComment(commentId);
        commentId = dbManager.getUserIdByCommentId(commentId);
        assertEquals(-1, commentId);
        dbManager.deleteUser(userId);
    }

    @Test
    public void testChangeRole() throws SQLException {
        User user = new User(email,username, password);
        dbManager.addUser(user);
        int userId =  dbManager.getUserIdByEmail(email);
        String role=null;
        admin.setNewRole(userId, "ADMIN");
        dbManager.changeUserRole(userId+1, "ADMIN");
        String selectQuery = "SELECT role FROM persons WHERE id = ?;";
        try(Connection connection = DbManager.open();
        PreparedStatement statement = connection.prepareStatement(selectQuery);){
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery();){
                if(resultSet.next()){
                    role = resultSet.getString(1);
                }
            }
        }
        assertEquals("ADMIN", role);
        dbManager.deleteUser(userId);

    }

}
