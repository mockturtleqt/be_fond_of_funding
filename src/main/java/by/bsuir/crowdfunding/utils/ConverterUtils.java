package by.bsuir.crowdfunding.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Objects.nonNull;

public class ConverterUtils {

    public static Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        if (nonNull(localDate)) {
            return Timestamp.valueOf(localDate.atTime(LocalTime.MIDNIGHT));
        } else return null;
    }
}
