package ru.timetable.dao.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.timetable.dao.AudienceDao;
import ru.timetable.dao.util.AudienceRowMapper;
import ru.timetable.domain.Audience;

@Component
@Repository
public class AudienceDaoImpl implements AudienceDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AudienceDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Audience> findById(Integer id) {
        String sql = """
                SELECT * FROM audience
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new AudienceRowMapper(), id)
                .stream().findFirst();
    }

    @Override
    public int insert(Audience audience) {
        String sql = """
                    INSERT INTO audience(number)
                    VALUES (?);
                """;

        return jdbcTemplate.update(sql, audience.getNumber());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM audience;");
    }

    @Override
    public Optional<Audience> findByNumber(String number) {
        String sql = """
                SELECT * FROM audience
                WHERE number = ?;
                """;

        return jdbcTemplate.query(sql, new AudienceRowMapper(), number)
                .stream().findFirst();
    }

    @Override
    public int count() {
        String sql = """
                SELECT COUNT(*) AS total
                FROM audience;
                """;
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);

        return Optional.ofNullable(total).orElse(0);
    }

}
