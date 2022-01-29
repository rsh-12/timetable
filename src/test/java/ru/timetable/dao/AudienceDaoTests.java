package ru.timetable.dao;
/*
 * Date: 18.01.2022
 * Time: 8:38 AM
 * */

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
import ru.timetable.domain.Audience;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AudienceDaoTests extends PostgreSqlTestBase {

    @Autowired
    private AudienceDao dao;

    private Audience savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        dao.insert(new Audience("112"));
        savedEntity = dao.findByNumber("112").orElse(null);

        assertNotNull(savedEntity);
        assertEquals(1, dao.count());
    }

    @Test
    public void findById() {
        Audience foundEntity = dao.findById(savedEntity.getId()).orElse(null);

        assertNotNull(foundEntity);
        assertNotNull(foundEntity.getId());

        assertNotNull(foundEntity.getCreatedAt());
        assertNotNull(foundEntity.getUpdatedAt());

        assertEquals(savedEntity, foundEntity);
    }

    @Test
    public void deleteById() {
        dao.deleteById(savedEntity.getId());
        assertEquals(0, dao.count());
    }

    @Test
    public void delete() throws InterruptedException {
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
