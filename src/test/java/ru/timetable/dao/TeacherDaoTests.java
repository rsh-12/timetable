package ru.timetable.dao;
/*
 * Date: 12.02.2022
 * Time: 9:49 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Teacher;
import ru.timetable.domain.util.Gender;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TeacherDaoTests extends PostgreSqlTestBase {

    @Autowired
    private TeacherDao dao;

    private Teacher savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        Teacher entity = Teacher.builder()
                .lastName("Волков")
                .firstName("Егор")
                .middleName("Михайлович")
                .email("volkov@mail.ru")
                .gender(Gender.MALE)
                .build();

        int result = dao.insert(entity);
        assertEquals(1, result);

        Optional<Teacher> optional = dao.findByEmail("volkov@mail.ru");
        assertTrue(optional.isPresent());
        savedEntity = optional.get();
    }

    @Test
    public void findByEmail_ShouldReturnOptionalEmpty() {
        assertTrue(dao.findByEmail("some@mail.com").isEmpty());
    }

}