package com.dezzzl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    public static final String SEARCH_BY_TAG = "SELECT * FROM images " +
            "INNER JOIN image_tags ON images.id = image_tags.image_id " +
            "INNER JOIN tags ON image_tags.tag_id = tags.id " +
            "WHERE tags.name = ?";
    public static final String SEARCH_BY_AUTHOR = "SELECT * FROM images " +
            "INNER JOIN persons ON user_id = persons.id " +
            "WHERE persons.username = ?";
    public static final String SEARCH_ALL_IMAGES = "SELECT * FROM images ";

    public static final String OFFSET_AND_LIMIT = "OFFSET ? LIMIT 2;";

    private final Connection connection;

    /**
     * Конструктор, связывающий класс с базой данных
     *
     * @param connection connection с базой данных
     */
    public SearchEngine(Connection connection) {
        this.connection = connection;
    }

    /**
     * Поиск изобразения по тегу
     *
     * @param offset количество записей из базы данных, которое пропуститься
     * @param tag    tag, по которому осуществляется поиск изображения
     * @return список изображений
     */
    public List<Image> getImagesWithTag(int offset, String tag) {
        List<Image> images = new ArrayList<>();
        String query = SEARCH_BY_TAG + " " + OFFSET_AND_LIMIT;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tag);
            statement.setInt(2, offset);
            createImage(offset, statement, images);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    /**
     * Поиск изображения по автору
     *
     * @param offset   количество записей из базы данных, которое пропуститься
     * @param username username автора, по которому осуществляется поиск изображения
     * @return список изображений
     */
    public List<Image> getImageWithAuthor(int offset, String username) {
        List<Image> images = new ArrayList<>();
        String query = SEARCH_BY_AUTHOR + " OFFSET ? LIMIT 2;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setInt(2, offset);
            createImage(offset, statement, images);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    /**
     * Поиск изобразения без фильтрации
     *
     * @param offset количество записей из базы данных, которое пропуститься
     * @return список изображений
     */
    List<Image> getAllImages(int offset) {
        List<Image> images = new ArrayList<>();
        String query = SEARCH_ALL_IMAGES;
        query += OFFSET_AND_LIMIT;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            createImage(offset, statement, images);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    private void createImage(int offset, PreparedStatement statement, List<Image> images) throws SQLException {

        try (ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp uploadDate = resultSet.getTimestamp("upload_date");
                int userId = resultSet.getInt("user_id");
                Author author = getPerson(userId, Author.CLASS);
                Image image = new Image(id, author, title, description, uploadDate);
                List<Tag> tags = getTags(id);
                List<Comment> comments = getComments(id);
                List<Rating> ratings = getRatings(id);
                image.setTags(tags);
                image.setComments(comments);
                image.setRaitings(ratings);
                images.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Image image : images) {
            System.out.println(image);
        }
    }

    private List<Comment> getComments(int imageId) {
        List<Comment> comments = new ArrayList<>();

        String query = "SELECT * FROM comments WHERE image_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int commentId = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    String text = resultSet.getString("text");
                    Timestamp date = resultSet.getTimestamp("date");
                    User user = getPerson(userId, User.CLASS);
                    Comment comment = new Comment(commentId, user, text, date);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    private List<Rating> getRatings(int imageId) {
        List<Rating> ratings = new ArrayList<>();

        String query = "SELECT * FROM ratings WHERE image_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int ratingId = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    int value = resultSet.getInt("value");
                    User user = getPerson(userId, User.CLASS);
                    Rating rating = new Rating(ratingId, user, value);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
    }

    private List<Tag> getTags(int imageId) {
        List<Tag> tags = new ArrayList<>();

        String query = "SELECT * FROM tags " +
                "INNER JOIN image_tags ON tags.id = image_tags.tag_id " +
                "WHERE image_tags.image_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, imageId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int tagId = resultSet.getInt("id");
                    String tagName = resultSet.getString("name");

                    Tag tag = new Tag(tagId, tagName);
                    tags.add(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }


    private <T extends User> T getPerson(int userId, Class<T> type) throws SQLException {
        String query = "SELECT * FROM persons WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    String role = resultSet.getString("role");

                    try {
                        return type.getConstructor(int.class, String.class, String.class, String.class)
                                .newInstance(userId, email, username, password);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка создания объекта", e);
                    }
                } else {
                    return null;
                }
            }
        }
    }

}

