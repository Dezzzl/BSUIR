package com.dezzzl.terator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EdgeIterator<T> implements Iterator<List<Node<T>>> {
    protected final Graph<T> graph;
    public EdgeIterator(Graph<T> graph) {
        this.graph = graph;
    }

    protected int currentIndex;

    @Override
    public boolean hasNext() {
        return currentIndex<graph.getEdges().size();
    }

    @Override
    public List<Node<T>> next() {
        return graph.getEdges().get(currentIndex++);
    }

    @Override
    public void remove() {
        graph.deleteEdge(graph.getEdges().get(currentIndex-1).get(0), graph.getEdges().get(currentIndex-1).get(1));
    }
}
