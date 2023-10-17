package com.dezzzl;

import java.util.ArrayList;
import java.util.List;

public class Author extends User implements ImageOperations {

    public static final Class<Author> CLASS = Author.class;

    private final List<Image> images = new ArrayList<>();

    /**
     * Конструктор, создающий автора по его id, email, username, password
     *
     * @param id       id автора
     * @param email    email автора
     * @param username username автора
     * @param password password автора
     */
    public Author(int id, String email, String username, String password) {
        super(id, email, username, password);
        setRole("AUTHOR");
    }

    /**
     * Конструктор, создающий автора по его email, username, password
     *
     * @param email    email автора
     * @param username username автора
     * @param password password автора
     */
    public Author(String email, String username, String password) {
        super(email, username, password);
        setRole("AUTHOR");
    }

    /**
     * Возвращает автора в виде строки
     *
     * @return автора в виде строки
     */
    @Override
    public String toString() {
        return getUsername();
    }

    /**
     * Загрузка изображения в базу данных
     *
     * @param title       название изображения
     * @param description описание изображения
     * @param userId      Id автора
     */
    public void uploadImage(String title, String description, int userId) {
        DbManager dbManager = new DbManager();
        dbManager.uploadImage(title, description, userId);
    }

    /**
     * Удаление изображения из базы данных
     *
     * @param imageId Id изображения
     */
    public void deleteImage(int imageId) {
        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByImageId(imageId) == this.getId()) {
            dbManager.deleteImage(imageId);
        }
    }

    /**
     * Добавление тега к изображению
     *
     * @param imageId Id изображения
     * @param tagName текст тега
     */
    public void addTag(int imageId, String tagName) {
        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByImageId(imageId) == this.getId()) {
            dbManager.addTagToImage(imageId, tagName);
        }
    }

    /**
     * Удаление тега из изображения
     *
     * @param imageId Id изображения
     * @param tagName текст тега
     */
    public void removeTag(int imageId, String tagName) {
        DbManager dbManager = new DbManager();
        if (dbManager.getUserIdByImageId(imageId) == this.getId()) {
            dbManager.removeTagFromImage(imageId, tagName);
        }
    }


}
