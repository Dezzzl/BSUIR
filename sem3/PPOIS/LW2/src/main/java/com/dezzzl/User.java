package com.dezzzl;

import java.sql.Connection;

public class User {
    public static final Class<User> CLASS = User.class;
    private Connection connection;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;

    private String username;

    private String password;

    public User(int id, String email, String username, String password) {
        this.id=id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void addComment(){

    }

    public void deleteComment(){

    }

    public void addRating(){

    }
    public void search(){

    }



}
