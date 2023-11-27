package com.dezzzl.dbmanagers;

import com.dezzzl.Util.OrderStatus;
import com.dezzzl.person.Person;
import com.dezzzl.person.Supplier;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;

import java.sql.*;
import java.util.*;

public class WarehouseDatabaseManager {

    private void insertOrderProducts(Connection connection, Map<Product, Integer> productsForOrder, Order order) throws SQLException {
        String insertOrderProductsQuery = "INSERT INTO Order_Product (quantity, order_id, product_id) VALUES (?, ?, ?)";

        try (PreparedStatement orderProductsStatement = connection.prepareStatement(insertOrderProductsQuery)) {
            for (Map.Entry<Product, Integer> entry : productsForOrder.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                orderProductsStatement.setInt(1, quantity);
                orderProductsStatement.setInt(2, order.getId());
                orderProductsStatement.setInt(3, product.getId());

                orderProductsStatement.addBatch();
            }

            orderProductsStatement.executeBatch();
        }
    }

    /**
     * Создает заказ в таблицу Order
     *
     * @param person  пользователь
     * @param productsForOrder список продуктов и их количество
     */
    public void createOrder(Map<Product, Integer> productsForOrder, Person person) {
        String insertQuery = "INSERT INTO Orders (order_date, status, person_id) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, "В ожидании");
            preparedStatement.setInt(3, person.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Создание заказа не удалось, ни одна запись не добавлена.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    Optional<Order> orderOptional = OrderDatabaseManager.getOrderById(orderId);
                    insertOrderProducts(connection, productsForOrder, orderOptional.get());
                    NotificationDatabaseManager.createStatusChangeNotification(orderOptional.get(), person, "В ожидании");
                } else {
                    throw new SQLException("Создание заказа не удалось, не удалось получить сгенерированный ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Обрабатывает заказ
     *
     * @param order заказ
     */
    public void processOrderForCustomer(Order order) {
        try {
            OrderDatabaseManager.updateOrderStatus(order.getId(), "Обрабатывается");
            Map<Product, Integer> missingProducts = checkStockAvailability(order);
            if (!missingProducts.isEmpty()) {
                NotificationDatabaseManager.insertShortageNotification(missingProducts, order);
                OrderDatabaseManager.updateOrderStatus(order.getId(), "Приостановлен");
                NotificationDatabaseManager.createStatusChangeNotification(order, order.getPerson(), "Приостановлен");
            } else {
                NotificationDatabaseManager.createStatusChangeNotification(order,order.getPerson(), "Обрабатывается");
                takeProductsFromStock(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void takeProductsFromStock(Order order) throws SQLException {
        Map<Product, Integer> productsInOrder = OrderDatabaseManager.getOrderProductsAndQuantity(order);

        for (Map.Entry<Product, Integer> entry : productsInOrder.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            int productId = product.getId();

            String stockUpdateQuery = "UPDATE Stock SET quantity = quantity - ? WHERE product_id = ?";

            try (Connection connection = ConnectionManager.open();
                 PreparedStatement stockUpdateStatement = connection.prepareStatement(stockUpdateQuery)) {

                stockUpdateStatement.setInt(1, quantity);
                stockUpdateStatement.setInt(2, productId);

                stockUpdateStatement.executeUpdate();
            }
        }
    }


    private Map<Product, Integer> checkStockAvailability(Order order) throws SQLException {
        Map<Product, Integer> productsShortInStock = new HashMap<>();

        String query = "SELECT sp.quantity AS stock_quantity, op.quantity AS order_quantity, p.id AS product_id, p.name AS product_name, p.description AS description " +
                "FROM Stock sp " +
                "JOIN Order_Product op ON sp.product_id = op.product_id " +
                "JOIN Product p ON sp.product_id = p.id " +
                "WHERE op.order_id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int stockQuantity = resultSet.getInt("stock_quantity");
                    int orderQuantity = resultSet.getInt("order_quantity");

                    if (stockQuantity < orderQuantity) {
                        int productId = resultSet.getInt("product_id");
                        String productName = resultSet.getString("product_name");
                        String description = resultSet.getString("description");
                        Product product = new Product(productId, productName, description);
                        productsShortInStock.put(product, orderQuantity - stockQuantity);
                    }
                }
            }
        }

        return productsShortInStock;
    }

    /**
     * Обрабатывает первый заказ из таблицы Orders со статусом "В ожидании"
     *
     * @param status статус заказа
     */
    public void processFirstPendingOrder(String status){
        String query = "SELECT * FROM Orders WHERE status = ? ORDER BY order_date LIMIT 1";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt("id");
                    Optional<Order> optionalOrder = OrderDatabaseManager.getOrderById(orderId);
                    if (optionalOrder.isPresent()) {
                        Order order = optionalOrder.get();
                        String role = PersonDatabaseManager.getPersonRole(order.getPerson());
                        if (Objects.equals(role, "Клиент")) {
                            processOrderForCustomer(order);
                        } else {
                            processOrderForSupplier(order);
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает первый заказ из таблицы Orders со статусом "Приостановлен"

     */
    public void processFirstSuspendedOrder(){
        processFirstPendingOrder("Приостановлен");
    }
    /**
     * Выполняет  заказы из таблицы Orders со статусом "Обрабатывается"
     *
     */
    public void completeOrders(){
        String query = "SELECT * FROM Orders WHERE status = 'Обрабатывается'";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                Optional<Order> optionalOrder = OrderDatabaseManager.getOrderById(orderId);

                if (optionalOrder.isPresent()) {
                    Order order = optionalOrder.get();
                    String role = PersonDatabaseManager.getPersonRole(order.getPerson());
                    finishOrder(order);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void finishOrder(Order order) {
        try {
            OrderDatabaseManager.updateOrderStatus(order.getId(), "Завершено");
            NotificationDatabaseManager.createStatusChangeNotification(order, order.getPerson(), "Завершено");
            TransactionDatabaseManager.createTransaction(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает заказ поставщика
     *
     * @param order заказ
     */
    public void processOrderForSupplier(Order order) {
        try {
            OrderDatabaseManager.updateOrderStatus(order.getId(), "Обрабатывается");
            NotificationDatabaseManager.createStatusChangeNotification(order, order.getPerson(), "Обрабатывается");
            putProductsToStock(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Кладет продукты из заказа на склад
     *
     * @param order заказ
     */
    public void putProductsToStock(Order order) {
        Map<Product, Integer> products = OrderDatabaseManager.getOrderProductsAndQuantity(order);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantityToAdd = entry.getValue();

            String query = "UPDATE Stock SET quantity = quantity + ? WHERE product_id = ?";

            try (Connection connection = ConnectionManager.open();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, quantityToAdd);
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Обновляет количество продуктов на складе после заказа
     * @param products список продуктов и их количество
     */
    public void updateStock(Map<Product, Integer> products) {
        String insertQuery = "INSERT INTO Stock (quantity, min_quantity, product_id) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, 3); // Предположим, что есть метод getMinQuantity()
                preparedStatement.setInt(3, product.getId());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает список продуктов на складе и их количество
     * @return список продуктов на складе и их количество
     */
    public Map<Product, Integer> getStockMap() {
        Map<Product, Integer> stockMap = new HashMap<>();
        String query = "SELECT s.quantity, p.id AS product_id, p.name AS product_name, p.description " +
                "FROM Stock s " +
                "JOIN Product p ON s.product_id = p.id";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");

                Product product = new Product(productId, productName, description);
                stockMap.put(product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockMap;
    }

}
