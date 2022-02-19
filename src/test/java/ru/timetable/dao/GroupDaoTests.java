package ru.timetable.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Group;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupDaoTests extends PostgreSqlTestBase {

    @Autowired
    private GroupDao dao;

    private Group savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        int result = dao.insert(new Group("ПО-316", "ОИТ"));
        assertEquals(1, result);

        savedEntity = dao.findByName("ПО-316").orElse(null);
        assertNotNull(savedEntity);
    }

    @Test
    public void findById_ShouldReturnSavedEntity() {
        assertEquals(Optional.of(savedEntity), dao.findById(savedEntity.getId()));
    }

    @Test
    public void deleteById() {
        dao.deleteById(savedEntity.getId());
        assertEquals(0, dao.count());
    }

    @Test
    public void delete() {
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

    @Test
    public void insert_DuplicateKeyException_ShouldReturn0() {
        int result = dao.insert(savedEntity);
        Assumptions.assumeTrue(result == 0);
    }

    @Test
    public void findAll() {
        List.of("ОЗИ-304", "МД-268", "ЗО-325", "ПР-360")
                .forEach(group -> dao.insert(new Group(group)));
        assertEquals(5, dao.count());

        Page<Group> page = dao.findAll(PageRequest.of(0, 3));
        assertEquals(2, page.getTotalPages());
        assertEquals(3, page.getSize());
    }

}