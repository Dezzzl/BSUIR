package com.dezzzl;

public class Admin extends User implements ImageOperations{
    public static final Class<Admin> CLASS = Admin.class;
    public Admin(int id,  String email, String username, String password) {
        super(id, email, username, password);
        setRole("ADMIN");
    }

    public  Admin(String email, String username, String password ){
        super(email, username, password);
        setRole("ADMIN");
    }

    public void deleteComment(int commentId){
        DbManager dbManager = new DbManager();
        dbManager.deleteComment(commentId);
    }

    public void deleteUser(int userId){
        DbManager dbManager = new DbManager();
        dbManager.deleteUser(userId);
    }

    public void deleteImage(int imageId){
        DbManager dbManager = new DbManager();
        dbManager.deleteImage(imageId);
    }

    public void addTag(int imageId, String newTag){
        DbManager dbManager = new DbManager();
        dbManager.addTagToImage(imageId, newTag);
    }

    public void removeTag(int imageId, String tagName){
        DbManager dbManager = new DbManager();
        dbManager.removeTagFromImage(imageId, tagName);
    }
    public void setNewRole(int userId, String newRole){
        DbManager dbManager = new DbManager();
        dbManager.changeUserRole(userId, newRole);
    }

}
