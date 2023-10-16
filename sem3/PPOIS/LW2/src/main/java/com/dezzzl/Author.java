package com.dezzzl;

import java.util.ArrayList;
import java.util.List;

public class Author extends User implements ImageOperations{

    public static final Class<Author> CLASS = Author.class;

    public Author(int id, String email, String username, String password) {
        super(id, email, username, password);
        setRole("AUTHOR");
    }

    public  Author(String email, String username, String password ){
        super(email, username, password);
        setRole("AUTHOR");
    }
    private final List<Image> images = new ArrayList<>();

    @Override
    public String toString() {
        return getUsername();
    }

    public void uploadImage(String title, String description, int userId) {
        DbManager dbManager = new DbManager();
        dbManager.uploadImage(title, description, userId);
    }

    public void deleteImage(int imageId) {
        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByImageId(imageId) == this.getId()) {
            dbManager.deleteImage(imageId);
        }
    }

    public void addTag(int imageId, String tagName){
        DbManager dbManager = new DbManager();
        if(dbManager.getUserIdByImageId(imageId)==this.getId()){
            dbManager.addTagToImage(imageId, tagName);
        }
    }

    public void removeTag(int imageId, String tagName){
        DbManager dbManager = new DbManager();
        if(dbManager.getUserIdByImageId(imageId)==this.getId()) {
            dbManager.removeTagFromImage(imageId, tagName);
        }
    }


}
