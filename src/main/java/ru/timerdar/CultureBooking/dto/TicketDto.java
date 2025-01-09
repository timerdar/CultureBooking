package ru.timerdar.CultureBooking.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketDto {
    private Long visitorId;
    private Long eventId;
    private String seat;
}
