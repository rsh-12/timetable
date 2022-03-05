package ru.timetable.dao;
/*
 * Date: 12.02.2022
 * Time: 9:49 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.util.List;
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
                .phone("89232183923")
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

    @Test
    public void findByEmail() {
        assertTrue(dao.findByEmail(savedEntity.getEmail()).isPresent());
    }

    @Test
    public void findByPhone_ShouldReturnOptionalEmpty() {
        assertTrue(dao.findByPhone("892323232343").isEmpty());
    }

    @Test
    public void findByPhone() {
        assertTrue(dao.findByPhone(savedEntity.getPhone()).isPresent());
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
    public void insertAll() {
        int before = dao.count();
        dao.insertAll(List.of(
                new Teacher("Дроздова", "Лариса", "Евгеньевна"),
                new Teacher("Михеева", "Елена", "Наумовна")
        ));

        assertEquals(before + 2, dao.count());
    }

    @Test
    public void insertAll_DuplicateKeyException() {
        int before = dao.count();
        dao.insertAll(List.of(
                new Teacher("Дроздова", "Лариса", "Евгеньевна"),
                new Teacher("Михеева", "Елена", "Наумовна"),
                savedEntity
        ));

        assertEquals(before, dao.count());
    }

    @Test
    public void insert_DuplicateKeyException() {
        int before = dao.count();
        int result = dao.insert(savedEntity);
        assertEquals(0, result);
        assertEquals(before, dao.count());
    }

    @Test
    public void findById() {
        assertTrue(dao.findById(savedEntity.getId()).isPresent());
    }

    @Test
    public void findById_ShouldReturnOptionalEmpty() {
        assertTrue(dao.findById(0).isEmpty());
    }

}
