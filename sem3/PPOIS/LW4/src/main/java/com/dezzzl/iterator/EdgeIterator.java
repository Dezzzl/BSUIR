package com.dezzzl.iterator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;
import java.util.List;

public class EdgeIterator<T> implements Iterator<List<Node<T>>> {
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
    public List<Node<T>> next() {
        return graph.getEdge(currentIndex++);
    }

    @Override
    public void remove() {
        graph.deleteEdge(graph.getEdge(currentIndex-1).get(0), graph.getEdge(currentIndex-1).get(1));
    }
}
