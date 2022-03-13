package ru.timetable.dao;
/*
 * Date: 18.01.2022
 * Time: 8:38 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Audience;

@SpringBootTest
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
    public void insert_ExistingEntity_ShouldReturn0() {
        assertEquals(0, dao.insert(savedEntity));
    }

    @Test
    public void findAll() {
        Consumer<String> consumer = s -> dao.insert(new Audience(s));
        IntStream.rangeClosed(102, 111)
                .mapToObj(String::valueOf)
                .forEach(consumer);

        int total = dao.count();
        assertEquals(11, total); // +savedEntity

        Page<Audience> result = dao.findAll(PageRequest.of(0, 5));
        assertEquals(total, result.getTotalElements());

        assertEquals(3, result.getTotalPages());
        assertEquals(5, result.getSize());
    }

    @Test
    public void insertAll() {
        int before = dao.count();

        List<Audience> audiences = IntStream.range(100, 105)
                .mapToObj(String::valueOf)
                .map(Audience::new)
                .toList();

        dao.insertAll(audiences);

        assertEquals(audiences.size() + before, dao.count());
    }

    @Test
    public void insertAll_ShouldRollbackTransaction() {
        int before = dao.count();

        List<Audience> audiences = List.of(new Audience("Gym"), savedEntity);
        dao.insertAll(audiences);

        assertEquals(before, dao.count());
    }

}
