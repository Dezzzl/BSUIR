package com.dezzzl.Util;

public enum OrderStatus {
    PENDING("В ожидании"),
    COMPLETED("Завершено"),
    PROCESSING("Обрабатывается"),
    CANCELED("Отменен"),
    SUSPENDED("Приостановлен");

        private final String status;

        OrderStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
}
