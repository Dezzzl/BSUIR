package com.dezzzl.person;

import com.dezzzl.dbmanagers.*;
import com.dezzzl.exceptions.PersonNotFoundException;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;
import com.dezzzl.warehouse.Warehouse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
PersonDatabaseManager personDatabaseManager = new PersonDatabaseManager();
    private static Warehouse warehouse=new Warehouse();
    private WarehouseDatabaseManager warehouseDatabaseManager = new WarehouseDatabaseManager();
    private static Supplier supplier = new Supplier(3, "SupplierName", "supplier@example.com");

    private  static Customer customer1 = new Customer(1, "CustomerName1", "customer1@example.com");

    private  static Customer customer2 = new Customer(2, "CustomerName2", "customer2@example.com");

    private  static Product product1 = new Product(1, "Product1", "Description1");
    private  static Product product2 = new Product(2, "Product2", "Description2");
    private static Product product3 = new Product(3, "Product3", "Description3");
    private static Product product4 = new Product(4, "Product4", "Description4");

    private static Map<Product, Integer> productMap = new HashMap<>();

    private static Map<Product, Integer> productMapInStock = new HashMap<>();

    @BeforeAll
    public static void setUp() {
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

    public static void truncateTables() {
        String truncateQuery = "TRUNCATE TABLE Notification, Transaction, Stock, Order_Product, Orders, Person, Product RESTART IDENTITY";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(truncateQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOrdersById() {
       List<Order> orders= customer1.getOrders();
       assertEquals(1, orders.size());
    }
    @Test
    void cancelOrder() {
        Optional<Order> orderOptional = OrderDatabaseManager.getOrderById(1);
        Order order = orderOptional.get();
        customer1.cancelOrder(order);
        orderOptional = OrderDatabaseManager.getOrderById(1);
        order = orderOptional.get();
        assertEquals("Отменен", order.getStatus());
    }

    @Test
    void getCustomerById() {
        int a =10;
        Optional<Customer> optionalCustomer= null;
        try {
            optionalCustomer = personDatabaseManager.getCustomerById(2);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
        Customer customer=optionalCustomer.get();
     assertEquals("CustomerName2", customer.getName());
    }

    @Test
    void getSupplierById() {
        int a =10;
        Optional<Supplier> optionalCustomer= null;
        try {
            optionalCustomer = personDatabaseManager.getSupplierById(3);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }
        Supplier customer=optionalCustomer.get();
        assertEquals("SupplierName", customer.getName());
    }

    @Test
    void testGetCustomerByIdThrowsException() {

        // Здесь предполагается, что у вас есть метод getSupplierById в вашем классе
        assertThrows(PersonNotFoundException.class, () -> {
            personDatabaseManager.getCustomerById(123);
        });
    }

    @Test
    void testGetSupplierByIdThrowsException() {

        // Здесь предполагается, что у вас есть метод getSupplierById в вашем классе
        assertThrows(PersonNotFoundException.class, () -> {
            personDatabaseManager.getSupplierById(123);
        });
    }

}