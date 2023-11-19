package com.dezzzl.person;

import com.dezzzl.dbmanagers.NotificationDatabaseManager;
import com.dezzzl.dbmanagers.OrderDatabaseManager;
import com.dezzzl.dbmanagers.PersonDatabaseManager;
import com.dezzzl.dbmanagers.WarehouseDatabaseManager;
import com.dezzzl.warehouse.Notification;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;

import java.util.List;
import java.util.Map;

public abstract class  Person {
    private final int id;
    private final String name;
    private final String email;

    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public void cancelOrder(Order order) {
        PersonDatabaseManager personDatabaseManager = new PersonDatabaseManager();
        personDatabaseManager.cancelOrder(order);
    }


    public abstract List<Notification> getNotifications();

    /**
     * Создает заказ
     *
     * @param products список продуктов и их количество
     */
    public abstract void createOrder(Map<Product, Integer> products);

    public List<Order> getOrders() {
        OrderDatabaseManager orderDatabaseManager = new OrderDatabaseManager();
        List<Order> orders = orderDatabaseManager.getOrdersByPersonId(this.getId());
        return orders;
    }

}
