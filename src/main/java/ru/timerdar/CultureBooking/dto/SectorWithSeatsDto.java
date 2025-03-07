package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.model.Sector;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class SectorWithSeatsDto {
    private Sector sector;
    private List<Seat> seats;
}
