package com.dezzzl.iterator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

import java.util.Iterator;
import java.util.List;

public class AdjacentEdgesIterator<T> implements Iterator<List<Node<T>>> {
    private final Graph<T> graph;

    private Node<T> node;

    private int currentIndex;

    private List<Node<T>> adjacentNodes;

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
    public List<Node<T>> next() {
        Node<T> adjacentNode = adjacentNodes.get(currentIndex++);
        if(graph.isEdgeInGraph(node, adjacentNode))return List.of(node, adjacentNode);
        else return List.of(adjacentNode, node);
    }
}
