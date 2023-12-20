package com.dezzzl.iterator;

import com.dezzzl.Edge;
import com.dezzzl.Graph;
import com.dezzzl.Node;
import com.dezzzl.exceptions.EdgeNotFoundException;

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
    public Edge<T> next() {
        try {
            return graph.getEdge(currentIndex--);
        } catch (EdgeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        try {
            graph.deleteEdge(graph.getEdge(currentIndex+1));
        } catch (EdgeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
