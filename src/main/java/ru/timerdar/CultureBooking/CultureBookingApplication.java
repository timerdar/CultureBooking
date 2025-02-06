package ru.timerdar.CultureBooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CultureBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureBookingApplication.class, args);
	}

}
