package com.dezzzl.warehouse;

import java.sql.Timestamp;

public class Notification {
    private final int id;
    private final String message;
    private final String notificationType;

    private final Timestamp createdDate;

    private final boolean isRead;

    private final int orderId;

    /**
     * Конструктор, создающий notification по его id, message, notificationType, createdDate, isRead, orderId
     *
     * @param id               id уведомления
     * @param message          message уведомления
     * @param notificationType notificationType уведомления
     * @param createdDate            createDate уведомления
     * @param isRead          прочитано ли уведомления
     * @param orderId orderId уведомления
     */
    public Notification(int id, String message, String notificationType, Timestamp createdDate, boolean isRead, int orderId) {
        this.id = id;
        this.message = message;
        this.notificationType = notificationType;
        this.createdDate = createdDate;
        this.isRead = isRead;
        this.orderId = orderId;
    }

    /**
     * Возвращает id уведомления
     *
     * @return id уведомления
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает message уведомления
     *
     * @return message уведомления
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает type уведомления
     *
     * @return type уведомления
     */
    public String getNotificationType() {
        return notificationType;
    }

    /**
     * Возвращает createdDate уведомления
     *
     * @return createdDate уведомления
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Возвращает orderId уведомления
     *
     * @return orderId уведомления
     */
    public int getOrderId() {
        return orderId;
    }

}
