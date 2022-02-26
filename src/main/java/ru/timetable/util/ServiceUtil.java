package ru.timetable.util;
/*
 * Date: 23.02.2022
 * Time: 8:58 AM
 * */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import ru.timetable.domain.util.Pair;

@Slf4j
@Component
public class ServiceUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // todo: create a custom exception
    public String toJsonString(Pair pair) {
        try {
            return objectMapper.writeValueAsString(pair);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }

    public  <T> T handleDuplicateKeyException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (DuplicateKeyException e) {
            log.error(e.getCause().getMessage());
            return null;
        }
    }

}
