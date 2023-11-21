package com.dezzzl;

public class Node<T>{
    private int index;
    private final T info;

    public int getIndex() {
        return index;
    }

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

    /**
     *Устанавливает номер строки для вершины в матрице инцидентности
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
