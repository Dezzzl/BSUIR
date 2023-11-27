package com.dezzzl.warehouse;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
    private final int id;

    private final String type;

    private final Timestamp date;
    private final int quantityChange;
    private final boolean isOrderCompleted;
    private final Order order;
    private final Product product;

    /**
     * Конструктор, создающий Transaction по его id, type, date, quantityChange, isOrderCompleted, orderId, productId
     *
     * @param id               id транзакции
     * @param type             тип транзакции
     * @param date             дата транзакции
     * @param quantityChange   изменение на складе
     * @param isOrderCompleted выполнен ли заказ или отменен
     * @param order           пользователь
     * @param product      продукт
     */
    public Transaction(int id, String type, Timestamp date, int quantityChange, boolean isOrderCompleted, Order order, Product product) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.quantityChange = quantityChange;
        this.isOrderCompleted = isOrderCompleted;
        this.order = order;
        this.product = product;
    }

    /**
     * Возвращает id транзакции
     *
     * @return id транзакции
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает тип транзакции
     *
     * @return тип транзакции
     */
    public String getType() {
        return type;
    }

    /**
     * Возвращает дату транзакции
     *
     * @return дату транзакции
     */
    public Date getDate() {
        return date;
    }

}
