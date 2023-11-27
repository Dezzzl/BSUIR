package com.dezzzl.Util;

public enum NotificationType {
    STATUS_CHANGE("Изменение Статуса"),
    SHORTAGE("Нехватка Товара");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

