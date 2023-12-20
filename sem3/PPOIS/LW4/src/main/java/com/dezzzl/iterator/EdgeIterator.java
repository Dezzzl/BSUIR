package com.dezzzl.iterator;

import com.dezzzl.Edge;
import com.dezzzl.Graph;
import com.dezzzl.Node;
import com.dezzzl.exceptions.EdgeNotFoundException;

import java.util.Iterator;
import java.util.List;

public class EdgeIterator<T> implements Iterator<Edge<T>> {
    protected final Graph<T> graph;
    public EdgeIterator(Graph<T> graph) {
        this.graph = graph;
    }

    protected int currentIndex;

    @Override
    public boolean hasNext() {
        return currentIndex<graph.getCountOfEdges();
    }

    @Override
    public Edge<T> next() {
        try {
            return graph.getEdge(currentIndex++);
        } catch (EdgeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        Edge<T>edge = null;
        try {
            edge = graph.getEdge(currentIndex-1);
        } catch (EdgeNotFoundException e) {
            throw new RuntimeException(e);
        }
        graph.deleteEdge(edge);
    }
}
