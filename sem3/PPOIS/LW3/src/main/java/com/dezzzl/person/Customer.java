package com.dezzzl.person;

import com.dezzzl.dbmanagers.NotificationDatabaseManager;
import com.dezzzl.dbmanagers.WarehouseDatabaseManager;
import com.dezzzl.warehouse.Notification;
import com.dezzzl.warehouse.Product;

import java.util.List;
import java.util.Map;

public class Customer extends Person {

    /**
     * Конструктор, создающий клиента по его id, email, name
     * @param id       id клиента
     * @param email    email клиента
     * @param name name клиента
     */
    public Customer(int id, String name, String email) {
        super(id, name, email);
    }

    /**
     * Возвращает непрочитанные уведомления для пользователя
     *
     * @return непрочитанные уведомления для пользователя
     */
    @Override
    public List<Notification> getNotifications() {
        return NotificationDatabaseManager.getUnreadNotificationsByPersonId(this.getId());
    }

    /**
     * Создает заказ
     *
     * @param products список продуктов и их количество
     */
    @Override
    public void createOrder(Map<Product, Integer> products) {
        WarehouseDatabaseManager warehouseDatabaseManager = new WarehouseDatabaseManager();
        warehouseDatabaseManager.createOrder(products, this);
    }

}
