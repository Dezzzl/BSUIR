package org.dezzzl.zoo.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
