package com.dezzzl;

public class Rating {
    private final User user;

    private int value;

    public Rating(User user, int value) {
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
