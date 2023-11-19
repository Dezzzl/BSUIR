package com.dezzzl.warehouse;

import java.util.Objects;

public class Product {
    private final int id;
    private final String name;

    private final String description;

    /**
     * Конструктор, создающий product по его id, name, description
     *
     * @param id               id продукта
     * @param name          название продукта
     * @param description описание продукта
     */
    public Product(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    /**
     * Возвращает id продукта
     *
     * @return id продукта
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает название продукта
     *
     * @return название продукта
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание продукта
     *
     * @return описание продукта
     */
    public String getDescription() {
        return description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
