package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Sector;

@NoArgsConstructor
@Setter
@Getter
public class SectorSeatsStatDto extends SeatStatDto{
    private Sector sector;

    public SectorSeatsStatDto(int reservedCount, int unreservedCount, Sector sector){
        super(reservedCount, reservedCount);
        this.sector = sector;
    }
}
