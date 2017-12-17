package by.bsuir.crowdfunding.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    private ApiInfo apiInfo() {
        //@formatter:off
        return new ApiInfoBuilder()
                .title("Be fond of funding")
                .description("Rest endpoints for a crowdfunding platform")
                .build();
        //@formatter:on
    }

    @Bean
    public Docket api() {
        //publish to default swagger url, e.g.: http://localhost:8080/swagger-ui.html
        //@formatter:off
        return new Docket(SWAGGER_2)
                .enable(swaggerEnable)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .select()
                .apis(basePackage("by.bsuir.crowdfunding"))
                .paths(any())
                .build();
        //@formatter:on
    }
}
