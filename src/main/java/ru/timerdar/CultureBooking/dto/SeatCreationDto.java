package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Seat;

@Getter @Setter
@AllArgsConstructor
public class SeatCreationDto {
    private String rowAndSeatNumber;
    private Long sectorId;

    public boolean isValid(){
        return rowAndSeatNumber.matches("\\d+-\\d+");
    }
}
