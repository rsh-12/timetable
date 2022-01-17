package ru.timetable.config;
/*
 * Date: 12.01.2022
 * Time: 12:58 AM
 * */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.util.PGobject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.timetable.domain.Pair;


@Configuration
public class DatasourceConfig extends AbstractJdbcConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.main")
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Override
    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        final List<Converter<?, ?>> converters = new ArrayList<>() {{
            add(PairToJson.INSTANCE);
            add(JsonToPair.INSTANCE);
        }};

        return new JdbcCustomConversions(converters);
    }

    @WritingConverter
    enum PairToJson implements Converter<Pair<String, String>, PGobject> {
        INSTANCE;

        @Override
        public PGobject convert(Pair source) {
            ObjectMapper objectMapper = new ObjectMapper();
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            try {
                jsonObject.setValue(objectMapper.writeValueAsString(source));
            } catch (SQLException | JsonProcessingException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }
    }

    @ReadingConverter
    enum JsonToPair implements Converter<PGobject, Pair<String, String>> {
        INSTANCE;

        @Override
        public Pair convert(PGobject pGobject) {
            ObjectMapper objectMapper = new ObjectMapper();
            String source = pGobject.getValue();
            try {
                return objectMapper.readValue(source, Pair.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
