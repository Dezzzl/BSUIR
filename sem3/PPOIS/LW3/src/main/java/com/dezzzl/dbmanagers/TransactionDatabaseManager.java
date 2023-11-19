package com.dezzzl.dbmanagers;

import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Transaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionDatabaseManager {
    /**
     * Создает транзакцию в таблицу Transaction после выполнеия или отмены заказа
     *
     * @param order заказ
     */
    public static void createTransaction(Order order) {
        String selectQuery = "SELECT o.status AS status, op.quantity AS quantity, o.id AS id, op.product_id AS product_id, p.role AS role FROM orders o " +
                "JOIN Order_Product op on o.id = op.order_id " +
                "JOIN person p ON o.person_id = p.id " +
                "WHERE o.id = ?";

        String insertQuery = "INSERT INTO transaction(quantity_change, transaction_type, transaction_date , order_id, product_id, is_order_completed)" +
                "VALUES (?,?,?,?,?,?)";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectQuery);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery)) {
            preparedStatement1.setInt(1, order.getId());
            try (ResultSet resultSet = preparedStatement1.executeQuery()) {
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    int orderId = resultSet.getInt("id");
                    int productId = resultSet.getInt("product_id");
                    String type = Objects.equals(resultSet.getString("role"), "Поставщик") ? "Поступление" : "Отдача";
                    Boolean isOrderCompleted = Objects.equals(resultSet.getString("status"), "Завершено");
                    preparedStatement2.setInt(1, quantity);
                    preparedStatement2.setString(2, type);
                    preparedStatement2.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement2.setInt(4, orderId);
                    preparedStatement2.setInt(5, productId);
                    preparedStatement2.setBoolean(6, isOrderCompleted);
                    preparedStatement2.addBatch();
                }
                preparedStatement2.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Возвращает тип транзакции(Поступление/Отдача)
     *
     * @param transactionId id транзакции
     */
    public static String getTransactionType(int transactionId) {
        String query = "SELECT transaction_type FROM Transaction WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, transactionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("transaction_type");
                } else {
                    // Обработка случая, когда транзакция с указанным ID не найдена
                    System.out.println("Транзакция с ID " + transactionId + " не найдена.");
                    return null; // или бросьте исключение, в зависимости от вашей логики
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка исключений
            return null; // или бросьте исключение, в зависимости от вашей логики
        }
    }

    /**
     * Возвращает транзакции по id заказа
     *
     * @param orderId id заказа
     */
    public static List<Transaction> getTransactionsByOrderId(int orderId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transaction WHERE order_id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int transactionId = resultSet.getInt("id");
                    String type = resultSet.getString("transaction_type");
                    Timestamp date = resultSet.getTimestamp("transaction_date");
                    int quantityChange = resultSet.getInt("quantity_change");
                    boolean isOrderCompleted = resultSet.getBoolean("is_order_completed");
                    int productId = resultSet.getInt("product_id");

                    Transaction transaction = new Transaction(transactionId, type, date, quantityChange, isOrderCompleted, orderId, productId);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
