package ru.timerdar.CultureBooking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {


    @Value("${ru.timerdar.frontend.uri}")
    private String uri;

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Разрешает доступ ко всем эндпоинтам
//                        .allowedOrigins(uri) // Адрес вашего React-приложения
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные методы
//                        .allowedHeaders("*")
//                        .exposedHeaders("*")
//                        .allowCredentials(false); // Если используются куки или сессии
//            }
//        };
//    }
}