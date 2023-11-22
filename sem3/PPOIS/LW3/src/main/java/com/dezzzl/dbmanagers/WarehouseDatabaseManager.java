package com.dezzzl.dbmanagers;

import com.dezzzl.person.Person;
import com.dezzzl.person.Supplier;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;

import java.sql.*;
import java.util.*;

public class WarehouseDatabaseManager {

    private void insertOrderProducts(Connection connection, Map<Product, Integer> productsForOrder, int orderId) throws SQLException {
        String insertOrderProductsQuery = "INSERT INTO Order_Product (quantity, order_id, product_id) VALUES (?, ?, ?)";

        try (PreparedStatement orderProductsStatement = connection.prepareStatement(insertOrderProductsQuery)) {
            for (Map.Entry<Product, Integer> entry : productsForOrder.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                orderProductsStatement.setInt(1, quantity);
                orderProductsStatement.setInt(2, orderId);
                orderProductsStatement.setInt(3, product.getId());

                orderProductsStatement.addBatch();
            }

            orderProductsStatement.executeBatch();
        }
    }

    /**
     * Создает заказ в таблицу Order
     *
     * @param personId id пользователя
     * @param productsForOrder список продуктов и их количество
     */
    public void createOrder(Map<Product, Integer> productsForOrder, int personId) {
        String insertQuery = "INSERT INTO Orders (order_date, status, person_id) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis())); // Или используйте текущую дату из вашего кода
            preparedStatement.setString(2, "В ожидании");
            preparedStatement.setInt(3, personId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Создание заказа не удалось, ни одна запись не добавлена.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    insertOrderProducts(connection, productsForOrder, orderId);
                    NotificationDatabaseManager.createStatusChangeNotification(orderId, personId, "В ожидании");
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
                NotificationDatabaseManager.insertShortageNotification(missingProducts, order.getId());
                OrderDatabaseManager. updateOrderStatus(order.getId(), "Приостановлен");
                NotificationDatabaseManager.createStatusChangeNotification(order.getId(), order.getPersonId(), "Приостановлен");
            } else {
                NotificationDatabaseManager.createStatusChangeNotification(order.getId(),order.getPersonId(), "Обрабатывается");
                takeProductsFromStock(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void takeProductsFromStock(Order order) throws SQLException {
        Map<Product, Integer> productsInOrder = OrderDatabaseManager.getOrderProductsAndQuantity(order.getId());

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

                        // Добавляем в Map с указанием недостающего количества
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
                        String role = PersonDatabaseManager.getPersonRole(order.getId());
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
                    String role = PersonDatabaseManager.getPersonRole(order.getId());
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
            NotificationDatabaseManager.createStatusChangeNotification(order.getId(), order.getPersonId(), "Завершено");
            TransactionDatabaseManager.createTransaction(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвразает всех поставщиков
     * @return все поставщики
     */
    public static List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM Person WHERE role = 'Поставщик'";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");

                Supplier supplier = new Supplier(id, name, email);
                suppliers.add(supplier);
            }
        }

        return suppliers;
    }

    /**
     * Обрабатывает заказ поставщика
     *
     * @param order заказ
     */
    public void processOrderForSupplier(Order order) {
        try {
            OrderDatabaseManager.updateOrderStatus(order.getId(), "Обрабатывается");
            NotificationDatabaseManager.createStatusChangeNotification(order.getId(), order.getPersonId(), "Обрабатывается");
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
        Map<Product, Integer> products = OrderDatabaseManager.getOrderProductsAndQuantity(order.getId());

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
     * Удаление продукта из таблицы Product по id
     * @param productId id продукта
     */
    public void deleteProductById(int productId) {
        String deleteQuery = "DELETE FROM Product WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    /**
     * Удаление заказа из таблицы Order по id
     * @param orderId id заказа
     */
    public void deleteOrderById(int orderId) {
        String deleteQuery = "DELETE FROM Orders WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }
    /**
     * Удаление пользователя из таблицы Person по id
     * @param personId id пользователя
     */
    public void deletePersonById(int personId) {
        String deleteQuery = "DELETE FROM Person WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }


    /**
     * Добавление пользователя в таблицу Person
     * @param person  пользователь
     * @param role роль пользователя
     */
    public void addPerson(Person person, String role) {
        String insertQuery = "INSERT INTO Person (email, name, role) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, person.getEmail());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, role);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавление продукта в таблицу Product
     * @param product  продукт
     */
    public void addProduct(Product product) {
        String insertQuery = "INSERT INTO Product (name, description) VALUES (?, ?)";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());

            preparedStatement.executeUpdate();
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
