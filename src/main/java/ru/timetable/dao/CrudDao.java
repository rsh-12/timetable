package ru.timetable.dao;

import java.util.Optional;

public interface CrudDao<T> {

    Optional<T> findById(Integer id);

    int insert(T entity);

    void deleteById(Integer id);

    void delete(T entity);

    void deleteAll();

    int count();

}
