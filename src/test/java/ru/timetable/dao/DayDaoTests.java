package ru.timetable.dao;
/*
 * Date: 28.01.2022
 * Time: 6:11 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Day;
import ru.timetable.domain.util.Weekday;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
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

    public void fillTable() {
        Arrays.stream(Weekday.values()).map(Day::new)
                .forEach(day -> dao.insert(day));
    }

}
