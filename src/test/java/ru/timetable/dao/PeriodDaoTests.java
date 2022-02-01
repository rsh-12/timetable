package ru.timetable.dao;
/*
 * Date: 01.02.2022
 * Time: 6:42 PM
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
import ru.timetable.domain.Period;
import ru.timetable.domain.util.Pair;
import ru.timetable.domain.util.PeriodNum;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PeriodDaoTests extends PostgreSqlTestBase {

    @Autowired
    private PeriodDao dao;

    private Period savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        savedEntity = Period.builder()
                .periodNum(PeriodNum.FIRST)
                .firstHalf(new Pair("08:30", "09:15"))
                .secondHalf(new Pair("09:20", "10:05"))
                .build();

        int result = dao.insert(savedEntity);
        assertEquals(1, result);

        savedEntity = dao.findByPeriodNum(PeriodNum.FIRST).orElse(null);
        assertNotNull(savedEntity);
    }

    @Test
    public void findById_ShouldReturnSavedEntity() {
        Period foundEntity = dao.findById(savedEntity.getId()).orElse(null);
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
