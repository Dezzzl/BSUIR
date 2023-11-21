package com.dezzzl.terator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;
import java.util.List;

public class AdjacentVerticesIterator<T> implements Iterator<Node<T>> {

    private final Graph<T> graph;

    private Node<T> node;

    private int currentIndex;

    private List<Node<T>> adjacentNodes;

    public AdjacentVerticesIterator(Graph<T> graph, Node<T> node) {
        this.graph = graph;
        this.node = node;
        adjacentNodes = graph.getIncidentNodes(node);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < adjacentNodes.size();
    }

    @Override
    public Node<T> next() {
        return adjacentNodes.get(currentIndex++);
    }
}
