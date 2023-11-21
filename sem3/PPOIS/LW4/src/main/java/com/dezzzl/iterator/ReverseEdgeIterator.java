package com.dezzzl.iterator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.List;

public class ReverseEdgeIterator<T> extends EdgeIterator<T> {

    public ReverseEdgeIterator(Graph<T> graph) {
        super(graph);
    }

    @Override
    public boolean hasNext() {
        return currentIndex>=0;
    }

    @Override
    public List<Node<T>> next() {
        return graph.getEdge(currentIndex--);
    }

    @Override
    public void remove() {
        graph.deleteEdge(graph.getEdge(currentIndex+1).get(0), graph.getEdge(currentIndex+1).get(1));
    }
}
