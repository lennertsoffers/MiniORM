package com.lennertsoffers.persistence.api.repositories;

public interface Repository<T> {
    void save(T obj);
}
