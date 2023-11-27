package com.dezzzl.dbmanagers;

import com.dezzzl.warehouse.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductDatabaseManager {

    /**
     * Возвращает продукт по названию
     *
     * @param name название продукта
     * @return продукт по названию
     */
    public static Optional<Product> getProductByName(String name) {
        String query = "SELECT * FROM  product WHERE name = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")
                    ));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Возвращает продукт по названию
     *
     * @param productId id продукта
     * @return продукт по названию
     */
    public static Optional<Product> getProductById(int productId) {
        String query = "SELECT * FROM  product WHERE id = ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")
                    ));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
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
     * Удаление продукта из таблицы Product по id
     * @param product продукт
     */
    public void deleteProductById(Product product) {
        String deleteQuery = "DELETE FROM Product WHERE id = ?";

        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
