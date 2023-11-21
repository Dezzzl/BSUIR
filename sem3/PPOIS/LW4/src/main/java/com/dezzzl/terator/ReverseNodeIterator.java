package com.dezzzl.terator;

import com.dezzzl.Graph;
import com.dezzzl.Node;

public class ReverseNodeIterator<T> extends NodeIterator<T>{

    public ReverseNodeIterator(Graph<T> graph) {
        super(graph);
        currentIndex = graph.getCountOfNodes()-1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >=0;
    }

    @Override
    public Node<T> next() {
        return graph.getNodes().get(currentIndex--);
    }

    @Override
    public void remove() {
        graph.deleteNode(graph.getNodes().get(currentIndex+1));
    }
}