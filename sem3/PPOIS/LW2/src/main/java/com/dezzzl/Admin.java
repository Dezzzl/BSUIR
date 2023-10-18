package com.dezzzl;

public class Admin extends User implements ImageOperations{
    public static final Class<Admin> CLASS = Admin.class;

    private static final DbManager dbManager = new DbManager();
    /**
     * Конструктор, создающий админа по его id, email, username, password
     *
     * @param id       id админа
     * @param email    email админа
     * @param username username админа
     * @param password password админа
     */
    public Admin(int id,  String email, String username, String password) {
        super(id, email, username, password);
        setRole("ADMIN");
    }

    /**
     * Конструктор, создающий админа по его email, username, password
     * @param email email админа
     * @param username username админа
     * @param password password админа
     * */
    public  Admin(String email, String username, String password ){
        super(email, username, password);
        setRole("ADMIN");
    }

    /**
     * Удаление комментария под изображением
     * @param commentId id комментария, который нужно удалить
     * */
    public void deleteComment(int commentId){
        dbManager.deleteComment(commentId);
    }


    /**
     * Удаление пользователя из базы данных
     * @param userId id пользователя, которого нужно удалить
     * */
    public void deleteUser(int userId){
        dbManager.deleteUser(userId);
    }
    /**
     * Удаление изображения по его id
     * @param imageId id изображения, которое нужно удалить
     * */
    public void deleteImage(int imageId){
        dbManager.deleteImage(imageId);
    }

    /**
     * Добавление тега к изображению
     * @param imageId id изображения, к которому нужно добавить тег
     * @param newTag текст тега
     * */
    public void addTag(int imageId, String newTag){
        dbManager.addTagToImage(imageId, newTag);
    }

    /**
     * Удаление тега из изображения
     * @param imageId id изображения, к из которого удаляется тег
     * @param tagName текст тега
     * */
    public void removeTag(int imageId, String tagName){
        dbManager.removeTagFromImage(imageId, tagName);
    }
    /**
     * Добавление тега к изображению
     * @param userId id пользователя, у которого меняют роль
     * @param newRole новая роль
     * */
    public void setNewRole(int userId, String newRole){
        dbManager.changeUserRole(userId, newRole);
    }



}
