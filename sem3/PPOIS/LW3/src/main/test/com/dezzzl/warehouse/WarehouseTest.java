package com.dezzzl.warehouse;

import com.dezzzl.dbmanagers.ConnectionManager;
import com.dezzzl.dbmanagers.OrderDatabaseManager;
import com.dezzzl.dbmanagers.TransactionDatabaseManager;
import com.dezzzl.dbmanagers.WarehouseDatabaseManager;
import com.dezzzl.person.Customer;
import com.dezzzl.person.Supplier;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

class WarehouseTest {
private static Warehouse warehouse=new Warehouse();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // Создаем объекты Date для начальной и конечной даты


private static Statistics statistics = new Statistics();
private WarehouseDatabaseManager warehouseDatabaseManager = new WarehouseDatabaseManager();
   private static Supplier supplier = new Supplier(3, "SupplierName", "supplier@example.com");

    // Создаем заказчика
    private  static Customer customer1 = new Customer(1, "CustomerName1", "customer1@example.com");

    private  static Customer customer2 = new Customer(2, "CustomerName2", "customer2@example.com");

    // Создаем продукты и добавляем их на склад (предположим, что есть класс Warehouse с методами setProducts и getProducts)
   private  static Product product1 = new Product(1, "Product1", "Description1");
  private  static Product product2 = new Product(2, "Product2", "Description2");
   private static Product product3 = new Product(3, "Product3", "Description3");
   private static Product product4 = new Product(4, "Product4", "Description4");

    private static Map<Product, Integer> productMap = new HashMap<>();

    private static Map<Product, Integer> productMapInStock = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        // Создаем Map из продуктов с указанием количества
        productMap.put(product1, 5);
        productMap.put(product2, 4);
        productMap.put(product3, 2);
        productMap.put(product4, 1);

        productMapInStock.put(product1, 11);
        productMapInStock.put(product2, 7);
        productMapInStock.put(product3, 5);
        productMapInStock.put(product4, 3);
        warehouse.addPerson(customer1, "Клиент");
        warehouse.addPerson(customer2, "Клиент");
        warehouse.addPerson(supplier, "Поставщик");
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.setProducts(productMapInStock);
        customer1.createOrder(productMap);
        customer2.createOrder(productMap);
    }

    @AfterAll
    static void clean(){
        warehouse.deleteOrderById(1);
        warehouse.deleteOrderById(2);
        warehouse.deleteOrderById(3);
        warehouse.deletePersonById(1);
        warehouse.deletePersonById(2);
        warehouse.deleteProductById(1);
        warehouse.deleteProductById(2);
        warehouse.deleteProductById(3);
        warehouse.deleteProductById(4);
        truncateTables();
    }
    @Test
    void getTotalQuantitySold(){
        int quantity = statistics.getTotalQuantitySold();
        assertEquals(0, quantity);
    }



    @Test
    void processFirstPendingOrder() {
int a =10;
//первый клиент(продуктов хватает)
        warehouse.processFirstPendingOrder();
        Optional<Order> orderOptional1 = OrderDatabaseManager.getOrderById(1);
        Order order1 = orderOptional1.get();
        assertEquals("Обрабатывается", order1.getStatus());
        List<Notification>notifications = customer1.getNotifications();
        assertEquals(2, notifications.size());
        Map<Product, Integer> stockAfterProcessing= warehouse.getStock();
        Map<Product, Integer> stockAfterProcessingExpected = new HashMap<>();
        stockAfterProcessingExpected.put(product1, 6);
        stockAfterProcessingExpected.put(product2, 3);
        stockAfterProcessingExpected.put(product3, 3);
        stockAfterProcessingExpected.put(product4, 2);
        assertEquals(stockAfterProcessingExpected, stockAfterProcessing);
        notifications.clear();
        stockAfterProcessingExpected.clear();
        stockAfterProcessing.clear();
        //второй клиент(не хватает 2 продукта)

        warehouse.processFirstPendingOrder();
        Optional<Order> orderOptional2 = OrderDatabaseManager.getOrderById(2);
        Order order2 = orderOptional2.get();
        assertEquals("Приостановлен", order2.getStatus());
        notifications = customer2.getNotifications();
        assertEquals(2, notifications.size());
        stockAfterProcessing= warehouse.getStock();
        stockAfterProcessingExpected = new HashMap<>();
        stockAfterProcessingExpected.put(product1, 6);
        stockAfterProcessingExpected.put(product2, 3);
        stockAfterProcessingExpected.put(product3, 3);
        stockAfterProcessingExpected.put(product4, 2);
        assertEquals(stockAfterProcessingExpected, stockAfterProcessing);

       List<Notification> supplierNotifications =  supplier.getNotifications();
        supplier.createOrder(productMap);
        warehouse.processFirstPendingOrder();
        Optional<Order> orderOptional3 = OrderDatabaseManager.getOrderById(3);
        Order order3 = orderOptional1.get();
        assertEquals("Обрабатывается", order3.getStatus());
        notifications = supplier.getNotifications();
        assertEquals(2, notifications.size());
        stockAfterProcessing= warehouse.getStock();
        stockAfterProcessingExpected = new HashMap<>();
        stockAfterProcessingExpected.put(product1, 11);
        stockAfterProcessingExpected.put(product2, 8);
        stockAfterProcessingExpected.put(product3, 5);
        stockAfterProcessingExpected.put(product4, 3);
        assertEquals(stockAfterProcessingExpected, stockAfterProcessing);
    }


    @Test
    void getTotalQuantityInOrders(){
        int quantity = statistics.getTotalQuantityInOrders();
        assertEquals(24, quantity);
    }

    @Test
    void getCompletedOrdersCount(){
        try {
            Date startDate = dateFormat.parse("10-11-2023");
            Date endDate = dateFormat.parse("01-12-2023");
            int quantity = statistics.getCompletedOrdersCount(startDate, endDate);
            assertEquals(2, quantity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void getCanceledOrdersCount(){
        try {
            Date startDate = dateFormat.parse("10-11-2023");
            Date endDate = dateFormat.parse("01-12-2023");
            int quantity = statistics.getCanceledOrdersCount(startDate, endDate);
            assertEquals(0, quantity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getPendingOrdersCount(){
        try {
            Date startDate = dateFormat.parse("10-11-2023");
            Date endDate = dateFormat.parse("01-12-2023");
            int quantity = statistics.getPendingOrdersCount(startDate, endDate);
            assertEquals(1, quantity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getTotalNotificationsCount(){
        try {
            Date startDate = dateFormat.parse("10-11-2023");
            Date endDate = dateFormat.parse("01-12-2023");
            int quantity = statistics.getTotalNotificationsCount();
            assertEquals(7, quantity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void completeOrders() {

        int a=10;
        warehouse.completeOrders();
        Optional<Order> orderOptional = OrderDatabaseManager.getOrderById(1);
        Order order = orderOptional.get();
        assertEquals("Завершено", order.getStatus());
        String transactionType = TransactionDatabaseManager.getTransactionType(1);
        assertEquals("Отдача", transactionType);
        List<Transaction> transactions=warehouse.getTransactionsByOrderId(1);
        assertEquals(4,transactions.size());
    }

    public static void truncateTables() {
        String truncateQuery = "TRUNCATE TABLE Notification, Transaction, Stock, Order_Product, Orders, Person, Product RESTART IDENTITY";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(truncateQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

}