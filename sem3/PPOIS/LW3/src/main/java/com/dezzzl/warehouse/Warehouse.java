package com.dezzzl.warehouse;

import com.dezzzl.dbmanagers.TransactionDatabaseManager;
import com.dezzzl.dbmanagers.WarehouseDatabaseManager;
import com.dezzzl.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Warehouse {

    WarehouseDatabaseManager warehouseDatabaseManager = new WarehouseDatabaseManager();
    /**
     * Обновляет количество продуктов на складе после заказа
     * @param products список продуктов и их количество
     */
    public void setProducts(Map<Product, Integer> products) {
        warehouseDatabaseManager.updateStock(products);
    }


    /**
     * Обрабатывает первый заказ из таблицы Orders со статусом "В ожидании"
     *
     */
    public void processFirstPendingOrder(){
        warehouseDatabaseManager.processFirstPendingOrder("В ожидании");

    }

    /**
     * Обрабатывает первый заказ из таблицы Orders со статусом "Приостановлен"

     */
    public void processFirstSuspendedOrder(){
        warehouseDatabaseManager.processFirstSuspendedOrder();
    }

    /**
     * Выполняет  заказы из таблицы Orders со статусом "Обрабатывается"
     *
     */
    public void completeOrders(){
        warehouseDatabaseManager.completeOrders();
    }

    /**
     * Удаление заказа из таблицы Order по id
     * @param id id заказа
     */
    public void deleteOrderById(int id){
        warehouseDatabaseManager.deleteOrderById(id);
    }

    /**
     * Удаление пользователя из таблицы Person по id
     * @param id id пользователя
     */
    public void deletePersonById(int id){
        warehouseDatabaseManager.deletePersonById(id);
    }

    /**
     * Удаление пользователя из таблицы Person по id
     * @param id id пользователя
     */
    public void deleteProductById(int id){
        warehouseDatabaseManager.deleteProductById(id);
    }


    /**
     * Добавление пользователя в таблицу Person
     * @param person  пользователь
     * @param role роль пользователя
     */
    public void addPerson(Person person, String role){
        warehouseDatabaseManager.addPerson(person, role);
    }

    /**
     * Добавление продукта в таблицу Product
     * @param product  продукт
     */
    public void addProduct(Product product){
        warehouseDatabaseManager.addProduct(product);
    }

    /**
     * Возвращает список продуктов на складе и их количество
     * @return список продуктов на складе и их количество
     */
    public Map<Product, Integer> getStock(){
        return warehouseDatabaseManager.getStockMap();
    }


    /**
     * Возвращает транзакции по id заказа
     *
     * @param orderId id заказа
     */
    public List<Transaction> getTransactionsByOrderId(int orderId){
       return TransactionDatabaseManager.getTransactionsByOrderId(orderId);
    }
}
