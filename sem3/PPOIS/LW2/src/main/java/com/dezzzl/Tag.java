package com.dezzzl;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private final String name;
    private final int id;

    /**
     * Конструктор, создающий тег по его id, name
     *
     * @param id   id тега
     * @param name название тега
     */
    public Tag(int id, String name) {
        this.name = name;
        this.id = id;
    }

    /**
     * Возвращает тег в виде строки
     *
     * @return тег в виде строки
     */
    @Override
    public String toString() {
        return name;
    }

}
