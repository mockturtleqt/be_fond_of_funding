package by.bsuir.crowdfunding.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

    public static String generateRandomFileName(String extension) {
        return RandomStringUtils.randomAlphabetic(16) + "." + extension;
    }

    public static String generateCsvDateBasedFileName(String prefix){
        return prefix + LocalDateTime.now().format(formatter) + ".csv";
    }
}
