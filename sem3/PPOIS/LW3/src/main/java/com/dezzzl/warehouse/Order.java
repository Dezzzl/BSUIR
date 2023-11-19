package com.dezzzl.warehouse;

import java.sql.Timestamp;
import java.util.Map;

public class Order {
    private final int id;

    private final Timestamp orderDate;

    private final String status;

    private final int personId;
    private Map<Product, Integer> products;

    /**
     * Конструктор, создающий order по его id, orderDate, status, personId
     *
     * @param id               id заказа
     * @param orderDate          message заказа
     * @param status notificationType заказа
     * @param personId            createDate заказа
     */
    public Order(int id, Timestamp orderDate, String status, int personId) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.personId = personId;
    }

    /**
     * Возвращает id заказа
     *
     * @return id заказа
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает продукты и их количество
     *
     * @return продукты и их количество
     */
    public Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Возвращает статус заказа
     *
     * @return статус заказа
     */
    public String getStatus() {
        return status;
    }

    /**
     * Возвращает время заказа
     *
     * @return время заказа
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }


    /**
     * Возвращает id пользователя
     *
     * @return время заказа
     */
    public int getPersonId() {
        return personId;
    }

}
