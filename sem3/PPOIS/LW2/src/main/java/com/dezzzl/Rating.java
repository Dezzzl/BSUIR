package com.dezzzl;

public class Rating {
    private final int id;
    private final User user;

    private final int value;

    /**
     * Конструктор, создающий рейтинг по его id, user, value
     *
     * @param id    id рейтинга
     * @param user  пользователь, оставивиший рейтинг
     * @param value численное значение рейтинга
     */
    public Rating(int id, User user, int value) {
        this.id = id;
        this.user = user;
        this.value = value;
    }

    /**
     * Возвращает численное значение рейтинга
     *
     * @return численное значение рейтинга
     */
    public int getValue() {
        return value;
    }
}
