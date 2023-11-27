package com.dezzzl.dbmanagers;

import java.sql.*;
import java.util.Date;

public class StatisticsDatabaseManager {
    /**
     * Возвращает количество продуктов ушедших из склада
     * @return количество продуктов пришедших/ушедших из склада
     */
    public static int getTotalQuantitySold(){
        String query = "SELECT SUM(quantity_change) FROM Transaction WHERE transaction_type = 'Отдача'";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
    /**
     * Возвращает количество продуктов пришедших/ушедших из склада
     * @return количество продуктов пришедших/ушедших из склада
     */
    public static int getTotalQuantityInOrders(){
        String query = "SELECT SUM(quantity) FROM Order_Product";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return 0; // Если товары в заказах не найдены
    }

    /**
     * Возвращает количество выполненных заказазов
     * @return количество выполненных заказазов
     */
    public static int getCompletedOrdersCount(Date startDate, Date endDate, String status){
        String query = "SELECT COUNT(*) FROM Orders WHERE status = ? AND order_date BETWEEN ? AND ?";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return 0; // Если выполненные заказы не найдены
    }

    /**
     * Возвращает количество отмененных заказазов
     * @return количество отмененных заказазов
     */
    public static int getCanceledOrdersCount(Date startDate, Date endDate){
        return getCompletedOrdersCount(startDate, endDate, "Отменен");
    }

    /**
     * Возвращает количество приостановленных заказазов
     * @return количество приостановленных заказазов
     */
    public static int getPendingOrdersCount(Date startDate, Date endDate){
        return getCompletedOrdersCount(startDate, endDate, "Приостановлен");
    }


    /**
     * Возвращает количество уведомлений
     * @return количество уведомлений
     */
    public static int getTotalNotificationsCount()  {
        String query = "SELECT COUNT(*) FROM Notification";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
}
