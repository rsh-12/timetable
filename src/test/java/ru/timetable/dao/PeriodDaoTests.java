package ru.timetable.dao;
/*
 * Date: 01.02.2022
 * Time: 6:42 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Period;
import ru.timetable.domain.factory.CommonPeriodFactory;
import ru.timetable.domain.factory.PeriodFactory;
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

        PeriodFactory factory = new CommonPeriodFactory();
        savedEntity = factory.getPeriod(PeriodNum.FIRST);

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

    @Test
    public void insert_DuplicateKeyException_ShouldReturn0() {
        int totalEntities = dao.count();
        assertEquals(0, dao.insert(savedEntity));
        assertEquals(totalEntities, dao.count());
    }

    @Test
    public void insertAll() {
        int before = dao.count();
        dao.insertAll(getPeriods(1));

        assertEquals(before + getPeriods(1).size(), PeriodNum.values().length);
        assertEquals(before + getPeriods(1).size(), dao.count());
    }

    @Test
    public void insertAll_DuplicateKeyException() {
        int before = dao.count();
        dao.insertAll(getPeriods(0));
        assertEquals(before, dao.count());
    }

    private List<Period> getPeriods(int skip) {
        PeriodFactory factory = new CommonPeriodFactory();
        return Arrays.stream(PeriodNum.values())
                .map(factory::getPeriod)
                .skip(skip).toList();
    }

}
