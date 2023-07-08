package de.lendmove.lendmoveapi.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 *  The basis method of a Implementation of a  service for a class T.
 * @param <T>
 */
public interface IService<T> {
    Collection<T> findAll();

    Optional<T> findById(Long id);

    Boolean saveOrUpdate(T t);

    boolean deleteById(Long id);

    T findByName(String name);
}