package com.dezzzl.dbmanagers;

import com.dezzzl.Util.OrderStatus;
import com.dezzzl.Util.PersonRole;
import com.dezzzl.exceptions.PersonNotFoundException;
import com.dezzzl.person.Customer;
import com.dezzzl.person.Person;
import com.dezzzl.person.Supplier;
import com.dezzzl.warehouse.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PersonDatabaseManager {
    /**
     * Возвращает клиента по id
     *
     * @param id  id клиента
     * @return клиента по id
     */
    public static Optional<Customer> getCustomerById(int id) throws PersonNotFoundException{
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
     * Возвращает клиента по id
     *
     * @param id  id пользователя
     * @return клиента по id
     */
    public static Optional<Person> getPersonById(int id) throws PersonNotFoundException{
        String query = "SELECT * FROM person WHERE id = ? ";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    ));
                } else {
                    throw new PersonNotFoundException("Пользователь не найден");
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
    public static Optional<Supplier> getSupplierById(int id) throws PersonNotFoundException{
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
     * @param person пользователь
     * @return поставщика по id
     */
    public static String getPersonRole(Person person) throws SQLException {
        String query = "SELECT role FROM Person WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, person.getId());

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
        if(Objects.equals(order.getStatus(), "Обрабатывается"))(new WarehouseDatabaseManager()).putProductsToStock(order);
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
}
