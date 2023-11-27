package com.dezzzl.iterator;

import com.dezzzl.Edge;
import com.dezzzl.Graph;
import com.dezzzl.Node;

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
        return graph.getEdge(currentIndex++);
    }

    @Override
    public void remove() {
        Edge<T>edge = graph.getEdge(currentIndex-1);
        graph.deleteEdge(edge);
    }
}
