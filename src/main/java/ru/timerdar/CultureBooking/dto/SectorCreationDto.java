package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SectorCreationDto {
    private String name;
    private String color;
    private Long eventId;
    private List<SeatCreationDto> seats;

    public boolean isValid(){
        return !(this.name.isEmpty() || this.color.isEmpty() || this.seats.isEmpty());
    }
}
