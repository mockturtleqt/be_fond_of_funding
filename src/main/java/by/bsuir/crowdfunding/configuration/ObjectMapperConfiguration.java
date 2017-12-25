package by.bsuir.crowdfunding.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.bsuir.crowdfunding.format.CustomObjectMapper;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }

}
