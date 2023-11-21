package com.dezzzl.terator;

import com.dezzzl.Graph;
import com.dezzzl.Node;
import com.dezzzl.terator.EdgeIterator;

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
        return graph.getEdges().get(currentIndex--);
    }

    @Override
    public void remove() {
        graph.deleteEdge(graph.getEdges().get(currentIndex+1).get(0), graph.getEdges().get(currentIndex+1).get(1));
    }
}
