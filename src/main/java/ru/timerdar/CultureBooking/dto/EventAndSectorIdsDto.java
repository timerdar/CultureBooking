package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventAndSectorIdsDto {
    private Long eventId;
    private Long sectorId;
}
