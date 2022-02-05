package ru.timetable.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Subject;
import ru.timetable.domain.util.SubjectType;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SubjectDaoTests extends PostgreSqlTestBase {

    @Autowired
    private SubjectDao dao;

    private Subject savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        int result = dao.insert(Subject.builder().name("УП").build());
        assertEquals(1, result);

        savedEntity = dao.findByNameAndSubjectType("УП", SubjectType.LECTURE)
                .orElse(null);
        assertNotNull(savedEntity);
    }

    @Test
    public void findById_ShouldReturnSavedEntity() {
        Subject foundEntity = dao.findById(savedEntity.getId()).orElse(null);
        assertNotNull(foundEntity);
        assertEquals(savedEntity, foundEntity);
    }

    @Test
    public void deleteById_ShouldDeleteEntityById() {
        dao.deleteById(savedEntity.getId());
        assertEquals(0, dao.count());
    }

    @Test
    public void delete_ShouldDeleteEntity() {
        dao.delete(savedEntity);
        assertEquals(0, dao.count());
    }

    @Test
    public void deleteById_ShouldNotFail() {
        dao.deleteById(null);
        dao.deleteById(savedEntity.getId());
        dao.deleteById(savedEntity.getId());
    }

    @Test
    public void delete_ShouldNotFail() {
        dao.delete(null);
        dao.delete(savedEntity);
        dao.delete(savedEntity);
    }

}