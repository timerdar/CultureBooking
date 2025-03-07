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

    public SeatStatDto plus(SeatStatDto seatStatDto){
        this.reservedCount += seatStatDto.reservedCount;
        this.unreservedCount += seatStatDto.unreservedCount;
        return this;
    }
}
