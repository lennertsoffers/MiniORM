package com.lennertsoffers.persistence.api.repositories;

import java.util.Optional;

public interface Repository<T> {
    void save(T obj);
    Optional<T> findById(int id);
}
