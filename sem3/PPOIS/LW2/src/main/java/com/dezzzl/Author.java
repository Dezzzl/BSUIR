package com.dezzzl;

import java.util.ArrayList;
import java.util.List;

public class Author extends User{

    public static final Class<Author> CLASS = Author.class;
    public Author(int id, String email, String username, String password) {
        super(id, email, username, password);
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }

    private List<Image> images=new ArrayList<>();
    @Override
    public String toString() {
        return getUsername();
    }
}
