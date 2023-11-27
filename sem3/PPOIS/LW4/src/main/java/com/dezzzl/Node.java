package com.dezzzl;

import java.util.Objects;

public class Node<T>{
    private final T info;


    public Node(T info) {
        this.info = info;
    }

    /**
     *Возвращает объект, которым параметризован граф
     * @return объект, которым параметризован граф
     */
    public T getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return  "info=" + info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(info, node.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
