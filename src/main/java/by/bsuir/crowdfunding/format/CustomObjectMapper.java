package by.bsuir.crowdfunding.format;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static by.bsuir.crowdfunding.utils.JsonPattern.DATE;
import static by.bsuir.crowdfunding.utils.JsonPattern.DATE_TIME;

public class CustomObjectMapper extends ObjectMapper {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE);

    public CustomObjectMapper() {
        super();
        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));

        this.registerModule(simpleModule);

        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
