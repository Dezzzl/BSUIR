package com.dezzzl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchEngineTest {
    private SearchEngine searchEngine = new SearchEngine(DbManager.open());

    private DbManager dbManager = new DbManager();

    private String tagName = "TestTag";

    private String username = "TestUsername";
    private String email = "test@gmail.com";
    private String password = "TestPassword";
    private String role = "AUTHOR";

    private String tittle1 = "TestTittle1";

    private String tittle2 = "TestTittle2";

    private String description1 = "TestDescription1";

    private String description2 = "TestDescription2";
    private int tagId = -1;

    private int userId=-1;
    Author author =new Author(email, username, password);

    private int imageId1= -1;

    private int imageId2= -1;


    @Before
    public void setUp() throws SQLException {
        dbManager.addTag(tagName);
        String selectQuery = "SELECT id FROM tags WHERE name = ?;";
        try (Connection connection = DbManager.open();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, tagName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tagId = resultSet.getInt(1);
                } else {
                    tagId = -1;
                }
            }
        }

        dbManager.addUser(author);
        userId = dbManager.getUserIdByEmail(email);
        author.setId(userId);
        author.uploadImage(tittle1, description1, userId);
        author.uploadImage(tittle2, description2, userId);

        try (Connection connection = DbManager.open();
             Statement statement1 = connection.createStatement();
             Statement statement2 = connection.createStatement();
             ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM images WHERE title = '" + tittle1 + "'");
             ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM images WHERE title = '" + tittle2 + "'")
        ) {
            if(resultSet1.next()) {
                imageId1 = resultSet1.getInt("id");
            }
            if(resultSet2.next()) {
                imageId2 = resultSet2.getInt("id");
            }
        }

        author.addTag(imageId1, tagName);
        author.addTag(imageId2, tagName);
    }

    @Test
    public void testGetImagesWithTag() {
        int a=10;
        List<Image> images1=(new SearchEngine(DbManager.open())).getImagesWithTag(0, tagName);
        List<Image> images2 =author.searchByTag(0, tagName);
        assertEquals(2, images1.size());
        assertEquals(2, images2.size());
    }

    @Test
    public void testGetAllImages() {
        List<Image> images1=(new SearchEngine(DbManager.open())).getAllImages(0);
        List<Image> images2 =author.getAllImages(0);
        assertEquals(2, images1.size());
        assertEquals(2, images2.size());
    }


    @Test
    public void testGetImagesWithAuthor(){
        int a=10;
        List<Image> images1=(new SearchEngine(DbManager.open())).getImageWithAuthor(0, username);
        List<Image> images2 =author.searchByAuthor(0, username);
        assertEquals(2, images1.size());
        assertEquals(2, images2.size());
    }

    @After
    public void clean() throws SQLException {
        author.deleteImage(imageId1);
        dbManager.deleteUser(userId);
        String deleteQuery = "DELETE FROM tags WHERE name = ?;";
        try (Connection connection = DbManager.open();
             PreparedStatement statement = connection.prepareStatement(deleteQuery);) {
            statement.setString(1, tagName);
            statement.executeUpdate();
        }
    }
}
