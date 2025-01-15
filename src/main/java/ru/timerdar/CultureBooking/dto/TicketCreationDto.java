package ru.timerdar.CultureBooking.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketCreationDto {
    private VisitorCreationDto visitor;
    private Long eventId;
    private Long sectorId;
    private Long seatId;

}
