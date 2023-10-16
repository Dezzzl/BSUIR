package com.dezzzl;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    public Tag( int id,String name) {
        this.name = name;
        this.id = id;
    }

    private String name;
    private int id;

    @Override
    public String toString() {
        return name;
    }

}
