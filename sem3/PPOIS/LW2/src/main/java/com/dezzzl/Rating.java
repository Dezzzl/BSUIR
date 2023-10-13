package com.dezzzl;

public class Rating {
    private int id;
    private final User user;

    private int value;


    public Rating(int id, User user, int value) {
        this.id=id;
        this.user = user;
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }
}
