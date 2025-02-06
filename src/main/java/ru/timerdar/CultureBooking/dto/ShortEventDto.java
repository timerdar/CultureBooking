package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class ShortEventDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
}
