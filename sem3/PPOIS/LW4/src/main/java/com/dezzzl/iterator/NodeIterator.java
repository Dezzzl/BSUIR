package com.dezzzl.iterator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;

public class NodeIterator<T> implements Iterator<Node<T>> {
    protected Graph<T> graph;

    public NodeIterator(Graph<T> graph) {
        this.graph = graph;
    }

    protected int currentIndex;

    @Override
    public boolean hasNext() {
        return currentIndex < graph.getNodes().size();
    }

    @Override
    public Node<T> next() {
        return graph.getNodeByIndex(currentIndex++).get();
    }

    @Override
    public void remove() {
        graph.deleteNode(graph.getNodeByIndex(currentIndex-1).get());
    }
}
