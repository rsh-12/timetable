package ru.timetable.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.timetable.PostgreSqlTestBase;
import ru.timetable.domain.Group;

@SpringBootTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupDaoTests extends PostgreSqlTestBase {

    @Autowired
    private GroupDao dao;

    private Group savedEntity;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
        assertEquals(0, dao.count());

        int result = dao.insert(new Group("ПО-316", "ОИТ"));
        assertEquals(1, result);

        savedEntity = dao.findByName("ПО-316").orElse(null);
        assertNotNull(savedEntity);
    }

}