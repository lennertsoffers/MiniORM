package com.lennertsoffers.persistence.api.repositories;

import java.util.Optional;

public interface Repository<K, T> {
    void save(T obj);
    Optional<T> findById(K id);
    Optional<T> updateById(T obj, K id);
    boolean delete(K id);
}
