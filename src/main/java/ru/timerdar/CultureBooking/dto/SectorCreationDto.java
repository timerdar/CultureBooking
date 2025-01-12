package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.model.Sector;

import java.util.List;

@Getter
@AllArgsConstructor
public class SectorCreationDto {
    private String name;
    private String color;
    private List<SeatCreationDto> seats;

    public boolean isValid(){
        return !(this.name.isEmpty() || this.color.isEmpty() || this.seats.isEmpty());
    }

    public Sector toSector(){
        return new Sector(1L, this.name, this.color);
    }
}
