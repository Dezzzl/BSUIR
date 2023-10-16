package com.dezzzl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

   private double calculateAverageRating() {
        double summaryRating = 0;
        if (ratings.isEmpty()) return 0;
        for (Rating rating : ratings) {
            summaryRating += rating.getValue();
        }
        return summaryRating / ratings.size();
    }


    public Author getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm  dd.MM.yy");
        String formattedDate = dateFormat.format(getUploadDate());
        StringBuilder st = new StringBuilder();
        st.append("Название: " + getTitle() + "\n");
        st.append("Описание: " + getDescription() + "\n");
        st.append("Автор: " + getAuthor().toString() + "\n");
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
