package ru.timerdar.CultureBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SectorCreationDto {
    private String name;
    private String color;
    private List<SeatCreationDto> seats;

    @JsonIgnore
    public boolean isValid(){
        return !(this.name.isEmpty() || this.color.isEmpty() || this.seats.isEmpty());
    }
}
