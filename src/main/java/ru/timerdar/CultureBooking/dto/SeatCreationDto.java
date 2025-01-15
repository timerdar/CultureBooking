package ru.timerdar.CultureBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Seat;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatCreationDto {
    private String rowAndSeatNumber;

    @JsonIgnore
    public boolean isValid(){
        return rowAndSeatNumber.matches("\\d+-\\d+");
    }
}
