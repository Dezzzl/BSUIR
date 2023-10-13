package com.dezzzl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image {

    private int id;

    private Author author;
    private String title;

    private String description;

    private Date uploadDate;

    private List<Comment> comments = new ArrayList<>();

    private List<Rating> ratings = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setRaitings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    private List<Tag> tags = new ArrayList<>();

    public Image(int id, Author author, String title, String description, Date uploadDate) {
        this.author=author;
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
        if (ratings.isEmpty()) return 0;
        for (Rating rating : ratings) {
            summaryRating += rating.getValue();
        }
        return summaryRating / ratings.size();
    }



    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm  dd.MM.yy");
        String formattedDate = dateFormat.format(uploadDate);
        StringBuilder st = new StringBuilder();
        st.append("Название: " + title + "\n");
        st.append("Описание: " + description + "\n");
        st.append("Автор: " + author.toString() + "\n");
        st.append("Дата публикации: " + formattedDate + "\n");
        st.append("Рейтинг: " + calculateAverageRating() + "\n");
        if (!tags.isEmpty()) {
            st.append("Теги: ");
            for (Tag tag : tags) {
                st.append(tag.toString());
                st.append(", ");
            }
            st.replace(st.length() - 2, st.length(), ".\n");
        }
        st.append("Комментарии: " + comments.size() + "\n");
        for (Comment comment : comments) {
            st.append(comment.toString());
        }
        return st.toString();
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
}
