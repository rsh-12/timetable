package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Teacher;

public interface TeacherDao extends CrudDao<Teacher> {

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByPhone(String phone);

}
