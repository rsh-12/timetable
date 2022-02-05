package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;

public interface SubjectDao extends CrudDao<Subject> {

    Optional<Subject> findByNameAndSubjectType(String name, SubjectType subjectType);

}
