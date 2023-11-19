package com.dezzzl.person;

import com.dezzzl.dbmanagers.NotificationDatabaseManager;
import com.dezzzl.dbmanagers.ProductDatabaseManager;
import com.dezzzl.dbmanagers.WarehouseDatabaseManager;
import com.dezzzl.person.Person;
import com.dezzzl.warehouse.Notification;
import com.dezzzl.warehouse.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Supplier extends Person {
    private Map<Product, Integer> missingProducts = new HashMap<>();

    /**
     * Конструктор, создающий поставщика по его id, email, name
     * @param id       id поставщика
     * @param email    email поставщика
     * @param name name поставщика
     */
    public Supplier(int id, String name, String email) {
        super(id, name, email);
    }

    private void parseShortageMessage(String message) {
        Map<Product, Integer> result = new HashMap<>();

        // Разбиваем строку по двоеточию и удаляем лишние пробелы
        String[] parts = message.split(":")[1].trim().split(",");

        for (String part : parts) {
            // Разбиваем информацию о продукте по открывающей и закрывающей скобкам
            String[] productInfo = part.trim().split("\\(");

            if (productInfo.length == 2) {
                // Получаем название продукта
                String productName = productInfo[0].trim();

                Optional<Product> productOptional = ProductDatabaseManager.getProductByName(productName);
                Product product = productOptional.get();
                // Получаем количество продукта
                int quantity = Integer.parseInt(productInfo[1].replaceAll("[^0-9]", ""));

                // Добавляем в Map
                missingProducts.put(product, quantity);
            }
        }
    }


    /**
     * Возвращает непрочитанные уведомления для поставщика и добавляет в свой список продукты и их количество, которые нужны складу
     *
     * @return непрочитанные уведомления для поставщика и добавляет в свой список продукты и их количество, которые нужны складу
     */
    @Override
    public List<Notification> getNotifications() {
        List<Notification> notifications = NotificationDatabaseManager.getUnreadNotificationsByPersonId(this.getId());
        for (Notification notification : notifications) {
            if (notification.getNotificationType().equals("Нехватка Товара")) {
                parseShortageMessage(notification.getMessage());
            }
        }
        return notifications;
    }

    /**
     * Создает заказ
     *
     * @param products список продуктов и их количество
     */
    @Override
    public void createOrder(Map<Product, Integer> products) {
        if (!missingProducts.isEmpty()) {
            missingProducts.forEach((product, quantity) ->
                    products.merge(product, quantity, Integer::sum));

            missingProducts.clear();
        }
        WarehouseDatabaseManager warehouseDatabaseManager = new WarehouseDatabaseManager();
        warehouseDatabaseManager.createOrder(products, this.getId());
    }

}
