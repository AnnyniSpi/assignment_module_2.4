package dev.annyni.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<ID, E> {

    List<E> findAll();

    Optional<E> findByID(ID id);

    boolean delete(ID id);

    E update(E entity);

    E save(E entity);

}
