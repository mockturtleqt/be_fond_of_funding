package by.bsuir.crowdfunding.configuration;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
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
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .select()
                .apis(basePackage("by.bsuir.crowdfunding"))
                .paths(any())
                .build();
        //@formatter:on
    }

    private ApiKey apiKey() {
        return new ApiKey("AUTHORIZATION", "authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("AUTHORIZATION", authorizationScopes));
    }
}
