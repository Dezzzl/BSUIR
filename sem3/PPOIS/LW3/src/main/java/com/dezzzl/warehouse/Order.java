package com.dezzzl.warehouse;

import com.dezzzl.person.Person;

import java.sql.Timestamp;
import java.util.Map;

public class Order {
    private final int id;

    private final Timestamp orderDate;

    private final String status;

    private final Person person;
    private Map<Product, Integer> products;

    /**
     * Конструктор, создающий order по его id, orderDate, status, personId
     *
     * @param id               id заказа
     * @param orderDate          дата заказа
     * @param status статус заказа
     * @param person пользователь, создавший заказ
     */
    public Order(int id, Timestamp orderDate, String status, Person person) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.person = person;
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
    public Person getPerson() {
        return person;
    }

}
