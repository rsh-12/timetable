package ru.timetable.dao;

import java.util.Optional;
import ru.timetable.domain.Group;

public interface GroupDao extends CrudDao<Group> {

    Optional<Group> findByName(String name);

}
