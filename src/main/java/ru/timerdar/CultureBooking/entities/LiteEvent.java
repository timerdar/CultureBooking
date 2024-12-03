package ru.timerdar.CultureBooking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class LiteEvent {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
}
