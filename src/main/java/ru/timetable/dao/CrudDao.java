package ru.timetable.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudDao<T> {

    Optional<T> findById(Integer id);

    int insert(T entity);

    void deleteById(Integer id);

    void delete(T entity);

    /**
     * Warning! Deletes all records from the table.
     */
    void deleteAll();

    int count();

    Page<T> findAll(Pageable pageable);

}
