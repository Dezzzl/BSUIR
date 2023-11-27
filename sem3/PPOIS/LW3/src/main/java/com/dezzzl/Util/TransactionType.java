package com.dezzzl.Util;

public enum TransactionType {
    INCOMING("Поступление"),
    OUTGOING("Отдача");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

