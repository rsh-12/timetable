package ru.timetable.repository;
/*
 * Date: 18.01.2022
 * Time: 8:38 AM
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
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Audience;

@DataJdbcTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AudienceRepositoryTests extends PostgreSqlTestBase {

    @Autowired
    private AudienceRepository repository;

    private Audience savedEntity;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        Audience newEntity = new Audience("112");
        savedEntity = repository.save(newEntity);

        assertEquals(newEntity.getNumber(), savedEntity.getNumber());
    }

    @Test
    public void findById() {
        Audience foundEntity = repository.findById(savedEntity.getId()).orElseThrow();
        assertEquals(savedEntity, foundEntity);
    }

}
