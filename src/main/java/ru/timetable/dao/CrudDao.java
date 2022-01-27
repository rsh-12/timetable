package ru.timetable.dao;

import java.util.Optional;

public interface CrudDao<T> {

    Optional<T> findById(Integer id);

    int insert(T t);

    void deleteAll();

    int count();

}
