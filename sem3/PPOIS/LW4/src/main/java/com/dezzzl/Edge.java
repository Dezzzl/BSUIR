package com.dezzzl;

import java.util.Objects;

public class Edge <T>{
    private final Node<T> from;
    private final Node<T> to;

    public Edge(Node<T> from, Node<T> to) {
        this.from = from;
        this.to = to;
    }

    public Node<T> getFrom() {
        return from;
    }

    public Node<T> getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

}
