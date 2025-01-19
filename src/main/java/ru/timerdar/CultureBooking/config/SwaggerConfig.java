package ru.timerdar.CultureBooking.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("my-api") // Название группы (отобразится в Swagger UI)
                .packagesToScan("ru.timerdar.CultureBooking.controller") // Укажите пакет ваших контроллеров
                .addOperationCustomizer(customize())
                .build();
    }


    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(false)
                        .description("JWT")
                        .name("Authorization"));
    }
}