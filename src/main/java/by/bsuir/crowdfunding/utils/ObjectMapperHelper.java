package by.bsuir.crowdfunding.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;

import by.bsuir.crowdfunding.exception.ObjectMapperException;
import by.bsuir.crowdfunding.format.CustomObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectMapperHelper {

    private static final ObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    public static <T> T readValue(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            String error = format("Could not read object '%s' from json: %s", clazz.getName(), json);
            log.error(error);
            throw new ObjectMapperException(error);
        }
    }

    public static <T> String writeValueAsString(T object) throws ObjectMapperException {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String errorMessage = ExceptionUtils.getRootCauseMessage(e);
            log.error(errorMessage);
            throw new ObjectMapperException(errorMessage);
        }
    }
}
