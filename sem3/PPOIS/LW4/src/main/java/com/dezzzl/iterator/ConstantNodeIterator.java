package com.dezzzl.iterator;

import com.dezzzl.Graph;

public class ConstantNodeIterator<T> extends NodeIterator<T>{

    public ConstantNodeIterator(Graph<T> graph) {
        super(graph);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Удаление невозможно");
    }
}
