package com.dezzzl.iterator;

import com.dezzzl.Edge;
import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;
import java.util.List;

public class AdjacentEdgesIterator<T> implements Iterator<Edge<T>> {
    private final Graph<T> graph;

    private final Node<T> node;

    private int currentIndex;

    private final List<Node<T>> adjacentNodes;

    public AdjacentEdgesIterator(Graph<T> graph, Node<T> node) {
        this.graph = graph;
        this.node = node;
        adjacentNodes = graph.getIncidentNodes(node);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < adjacentNodes.size();
    }

    @Override
    public Edge<T> next() {
        Node<T> adjacentNode = adjacentNodes.get(currentIndex++);
        if(graph.isEdgeInGraph(new Edge<>(node, adjacentNode)))return new Edge<>(node, adjacentNode);
        else return new Edge<>(adjacentNode, node);
    }
}
