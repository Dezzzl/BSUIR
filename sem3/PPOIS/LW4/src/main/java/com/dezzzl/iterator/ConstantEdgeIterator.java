package com.dezzzl.iterator;

import com.dezzzl.Graph;

public class ConstantEdgeIterator<T> extends EdgeIterator<T>{

    public ConstantEdgeIterator(Graph<T> graph) {
        super(graph);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Удаление невозможно");
    }
}
