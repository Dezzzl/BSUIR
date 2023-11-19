package com.dezzzl.dbmanagers;

import com.dezzzl.exceptions.PersonNotFoundException;
import com.dezzzl.person.Customer;
import com.dezzzl.person.Supplier;
import com.dezzzl.warehouse.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonDatabaseManager {
    /**
     * Возвращает клиента по id
     *
     * @param id  id клиента
     * @return клиента по id
     */
    public Optional<Customer> getCustomerById(int id) throws PersonNotFoundException{
        String query = "SELECT * FROM person WHERE id = ? AND role = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "Клиент");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    ));
                } else {
                    throw new PersonNotFoundException("Клиент не найден");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



    /**
     * Возвращает поставщика по id
     *
     * @param id  id поставщика
     * @return поставщика по id
     */
    public Optional<Supplier> getSupplierById(int id) throws PersonNotFoundException{
        String query = "SELECT * FROM person WHERE id = ? AND role = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "Поставщик");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Supplier(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    ));
                } else {
                    throw new PersonNotFoundException("Поставщик не найден");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Возвращает поставщика по id
     *
     * @param id  id поставщика
     * @return поставщика по id
     */
    static String getPersonRole(int id) throws SQLException {
        String query = "SELECT role FROM Person WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                }
            }
        }

        return null;
    }

    /**
     * Устанавливает значение заказа "Отменен" и создает транзакцию
     *
     * @param order заказ
     */
    public void cancelOrder(Order order){
        String query = "UPDATE Orders SET status = 'Отменен' WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, order.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        TransactionDatabaseManager.createTransaction(order);
    }
}
