package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
public class SeatStatDto {
    private int reservedCount;
    private int unreservedCount;
}
