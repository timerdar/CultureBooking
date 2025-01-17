package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ErrorMessage {
    private String error;
    private String message;
    private String path;
}
