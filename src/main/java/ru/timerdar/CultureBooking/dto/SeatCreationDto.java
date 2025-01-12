package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Seat;

@Getter @Setter
@AllArgsConstructor
public class SeatCreationDto {
    private String rowAndSeatNumber;

    public boolean isValid(){
        return rowAndSeatNumber.matches("\\d+-\\d+");
    }

    public Seat toSeat(){
        return new Seat(1L, this.rowAndSeatNumber, false);
    }
}
