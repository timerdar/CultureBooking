package ru.timerdar.CultureBooking.config;

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
                .build();
    }
}