package ru.timetable.repository;
/*
 * Date: 17.01.2022
 * Time: 7:06 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.domain.Day;
import ru.timetable.domain.Weekday;

@DataJdbcTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DayRepositoryTests extends PostgreSqlTestBase {

    @Autowired
    private DayRepository repository;

    private Day after;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        Day before = new Day(Weekday.MONDAY);
        after = repository.save(before);

        assertEquals(before.getName(), after.getName());
    }

    @Test
    public void findByName() {
        Day foundEntity = repository.findByName(Weekday.MONDAY).orElseThrow();
        assertEquals(after, foundEntity);
    }

}
