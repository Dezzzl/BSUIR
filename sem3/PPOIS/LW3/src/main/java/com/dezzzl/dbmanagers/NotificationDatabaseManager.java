package com.dezzzl.dbmanagers;

import com.dezzzl.Util.NotificationType;
import com.dezzzl.person.Person;
import com.dezzzl.person.Supplier;
import com.dezzzl.warehouse.Notification;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NotificationDatabaseManager {

    /**
     * Добавляет в таблицу Notification уведомление об смене статуса заказа
     *
     * @param order заказ
     * @param person id пользователь
     * @param newStatus новый статус заказа
     */
    public static void createStatusChangeNotification(Order order, Person person, String newStatus) throws SQLException {
        String message = "Статус вашего заказа изменен на: " + newStatus;
        String query = "INSERT INTO Notification (message, notification_type, created_at, order_id, person_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, message);
            preparedStatement.setString(2, "Изменение Статуса");
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, order.getId());
            preparedStatement.setInt(5, person.getId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Возвращает непрочитанные уведомления для пользователя
     *
     * @param personId  id пользователя
     * @return непрочитанные уведомления для пользователя
     */
    public static List<Notification> getUnreadNotificationsByPersonId(int personId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notification WHERE person_id = ? AND is_read = FALSE ORDER BY created_at DESC";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String message = resultSet.getString("message");
                    String notificationType = resultSet.getString("notification_type");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    boolean isRead = resultSet.getBoolean("is_read");
                    int orderId = resultSet.getInt("order_id");
                   Optional<Order> orderOptional= OrderDatabaseManager.getOrderById(orderId);
                    Notification notification = new Notification(id, message, notificationType, createdAt, isRead, orderOptional.get());
                    notifications.add(notification);
                    markNotificationAsRead(notification);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    private static void markNotificationAsRead(Notification notification) throws SQLException {
        String updateQuery = "UPDATE Notification SET is_read = TRUE WHERE id = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, notification.getId());
            updateStatement.executeUpdate();
        }
    }

    /**
     * Добавляет в таблицу Notification уведомление о нехватке продуктов
     *
     * @param productsShortInStock   продукты и их количество
     * @param order заказ
     */
    public static void insertShortageNotification(Map<Product, Integer> productsShortInStock, Order order) throws SQLException {
        List<Supplier> suppliers = PersonDatabaseManager.getAllSuppliers();
        String insertQuery = "INSERT INTO Notification (message, notification_type, created_at, order_id, person_id) VALUES (?, ?, ?, ?, ?)";
        String message = constructShortageMessage(productsShortInStock);
        try (Connection connection = ConnectionManager.open();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            for (Supplier supplier : suppliers) {
                String notificationType = "Нехватка Товара";
                insertStatement.setString(1, message);
                insertStatement.setString(2, notificationType);
                insertStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                insertStatement.setInt(4, order.getId());
                insertStatement.setInt(5, supplier.getId());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private static String constructShortageMessage(Map<Product, Integer> productsShortInStock) {
        StringBuilder messageBuilder = new StringBuilder("Недостаточно следующих продуктов на складе: ");

        for (Map.Entry<Product, Integer> entry : productsShortInStock.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            messageBuilder.append(product.getName()).append(" (").append(quantity).append(" шт.), ");
        }

        if (!productsShortInStock.isEmpty()) {
            messageBuilder.delete(messageBuilder.length() - 2, messageBuilder.length());
        }

        return messageBuilder.toString();
    }
}
