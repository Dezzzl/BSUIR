package com.dezzzl.dbmanagers;

import com.dezzzl.exceptions.PersonNotFoundException;
import com.dezzzl.person.Person;
import com.dezzzl.warehouse.Order;
import com.dezzzl.warehouse.Product;

import java.sql.*;
import java.util.*;

public class OrderDatabaseManager {
    /**
     * Возвращает заказ по id
     *
     * @param orderId  id заказа
     * @return заказ
     */
    public static Optional<Order> getOrderById(int orderId) {
        String query = "SELECT * FROM Orders WHERE id = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                   int personId =  resultSet.getInt("person_id");
                   Optional<Person> personOptional  = PersonDatabaseManager.getPersonById(personId);
                    return Optional.of(new Order(
                            resultSet.getInt("id"),
                            resultSet.getTimestamp("order_date"),
                            resultSet.getString("status"),
                            personOptional.get()
                    ));
                }
            } catch (PersonNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();    }

    /**
     * Возвращает продукты и их количество для данного заказа
     *
     * @param order заказ
     * @return продукты и их количество для данного заказа
     */
    public static Map<Product, Integer> getOrderProductsAndQuantity(Order order) {
        Map<Product, Integer> orderProductsAndQuantity = new HashMap<>();

        String query = "SELECT * " +
                "FROM Order_Product op " +
                "JOIN Product p ON op.product_id = p.id " +
                "WHERE op.order_id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    int productId = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    String productDescription = resultSet.getString("description");
                    Product product = new Product(productId, productName, productDescription);
                    orderProductsAndQuantity.put(product, quantity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderProductsAndQuantity;
    }

    /**
     * Обновляет статус заказа в таблице Orders
     *
     * @param orderId  id заказа
     * @param newStatus новый статус заказа
     */
   public static void updateOrderStatus(int orderId, String newStatus) throws SQLException {
        String updateQuery = "UPDATE Orders SET status = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, newStatus);
            updateStatement.setInt(2, orderId);
            updateStatement.executeUpdate();
        }
    }

    /**
     * Возвращает заказы по id пользователя
     *
     * @param personId id пользователя
     */
    public List<Order> getOrdersByPersonId(int personId) {
        List<Order> orders = new ArrayList<>();

        String query = "SELECT * FROM Orders WHERE person_id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = mapResultSetToOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException | PersonNotFoundException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException, PersonNotFoundException {
        int orderId = resultSet.getInt("id");
        Timestamp orderDate = resultSet.getTimestamp("order_date");
        String status = resultSet.getString("status");
        int personId = resultSet.getInt("person_id");
        Optional<Person> personOptional = PersonDatabaseManager.getPersonById(personId);
        return new Order(orderId, orderDate, status, personOptional.get());
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
            e.printStackTrace();
        }
    }
}
