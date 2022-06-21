package com.lennertsoffers.persistence.api.repositories;

import java.util.Optional;

public interface Repository<K, T> {
    void save(T obj);
    Optional<T> findById(K id);
}
