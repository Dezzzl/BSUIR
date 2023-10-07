package com.dezzzl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image {
    private int id;
    private String title;

    private String description;

    private Date uploadDate;

    private final List<Comment> comments = new ArrayList<>();

    private final List<Rating> ratings = new ArrayList<>();

    private final List<Tag> tags = new ArrayList<>();

    public Image(int id, String title, String description, Date uploadDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
    }

    void addComment(Comment comment) {
        comments.add(comment);
    }

    void removeComment(Comment comment) {
        comments.remove(comment);
    }

    void addTag(Tag tag) {
        tags.add(tag);
    }

    void removeTag(Tag tag) {
        tags.remove(tag);
    }

    double calculateAverageRating() {
        double summaryRating = 0;
        if (ratings.isEmpty()) return -1;
        for (Rating rating : ratings) {
            summaryRating += rating.getValue();
        }
        return summaryRating / ratings.size();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("Название: " + title + "\n");
        st.append("Описание: " + description + "\n");
        st.append("Дата публикации: " + uploadDate + "\n");
        st.append("Рейтинг: " + calculateAverageRating() + "\n");
        st.append("Теги: ");
        for (Tag tag : tags) {
            st.append(tags.toString());
            st.append(", ");
        }
        st.replace(st.length() - 1, st.length(), ".\n");
        st.append("Комментарии: " + comments.size() + "\n");
        for (Comment comment : comments) {
            st.append(comment.toString());
        }
        return st.toString();
    }
}
