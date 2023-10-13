package com.dezzzl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private int id;
    private final User user;
    private final String text;
    private final Date date;

    public Comment(int id, User user, String text, Date date) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        StringBuilder st=new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm  dd.MM.yy");
        String formattedDate = dateFormat.format(date);
        st.append(user.getUsername()+" отправил в "+formattedDate+"\n");
        st.append(text+"\n");
        return st.toString();
    }
}
