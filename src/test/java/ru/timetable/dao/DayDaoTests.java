package ru.timetable.dao;
/*
 * Date: 28.01.2022
 * Time: 6:11 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.timetable.domain.util.Weekday.THURSDAY;
import static ru.timetable.domain.util.Weekday.WEDNESDAY;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Day;
import ru.timetable.domain.util.Weekday;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DayDaoTests extends PostgreSqlTestBase {

    @Autowired
    private DayDao dao;

    private Day savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        int result = dao.insert(new Day(Weekday.MONDAY));
        assertEquals(1, result);

        savedEntity = dao.findByWeekday(Weekday.MONDAY).orElse(null);
        assertNotNull(savedEntity);
    }

    @Test
    public void findById_ShouldReturnSavedEntity() {
        assertEquals(Optional.of(savedEntity), dao.findById(savedEntity.getId()));
    }

    @Test
    public void initTable() {
        fillTable();

        int actualQuantity = dao.count();
        int expectedQuantity = Weekday.values().length;

        assertEquals(expectedQuantity, actualQuantity);
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

    // Sorting has not been implemented yet
    @Test
    public void findAll_ShouldReturnOneEntity() {
        fillTable();

        List<Day> entities = dao.findAll(PageRequest.of(0, 3)).getContent();
        assertFalse(entities.isEmpty());
        assertEquals(3, entities.size());
    }

    @Test
    public void insertAll() {
        int before = dao.count();

        List<Day> days = List.of(new Day(THURSDAY), new Day(WEDNESDAY));
        dao.insertAll(days);

        assertEquals(before+ days.size(), dao.count());
    }

    @Test
    public void insertAll_DuplicateKeyException() {
        int before = dao.count();

        List<Day> days = List.of(new Day(THURSDAY), new Day(WEDNESDAY), savedEntity);
        dao.insertAll(days);

        assertEquals(before, dao.count());
    }

    private void fillTable() {
        Arrays.stream(Weekday.values()).map(Day::new)
                .forEach(day -> dao.insert(day));
    }

}
