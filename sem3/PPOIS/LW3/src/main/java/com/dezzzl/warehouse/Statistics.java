package com.dezzzl.warehouse;

import com.dezzzl.Util.OrderStatus;
import com.dezzzl.dbmanagers.StatisticsDatabaseManager;

import java.util.Date;

public class Statistics {
    /**
     * Возвращает количество продуктов ушедших из склада
     * @return количество продуктов пришедших/ушедших из склада
     */
    public int getTotalQuantitySold(){
        return StatisticsDatabaseManager.getTotalQuantitySold();
    }

    /**
     * Возвращает количество продуктов пришедших/ушедших из склада
     * @return количество продуктов пришедших/ушедших из склада
     */
    public int getTotalQuantityInOrders(){
        return StatisticsDatabaseManager.getTotalQuantityInOrders();
    }

    /**
     * Возвращает количество выполненных заказазов
     * @return количество выполненных заказазов
     */
    public int getCompletedOrdersCount(Date startDate, Date endDate){
        return StatisticsDatabaseManager.getCompletedOrdersCount(startDate, endDate, OrderStatus.COMPLETED.getStatus());
    }
    /**
     * Возвращает количество отмененных заказазов
     * @return количество отмененных заказазов
     */

    public int getCanceledOrdersCount(Date startDate, Date endDate){
        return StatisticsDatabaseManager.getCanceledOrdersCount(startDate, endDate);
    }

    /**
     * Возвращает количество приостановленных заказазов
     * @return количество приостановленных заказазов
     */
    public int getPendingOrdersCount(Date startDate, Date endDate){
        return StatisticsDatabaseManager.getPendingOrdersCount(startDate, endDate);
    }

    /**
     * Возвращает количество уведомлений
     * @return количество уведомлений
     */
    public int getTotalNotificationsCount(){
       return StatisticsDatabaseManager.getTotalNotificationsCount();
    }

}
