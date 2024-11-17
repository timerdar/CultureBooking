package ru.timerdar.CultureBooking.requests;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketRequest {
    private Long visitorId;
    private Long eventId;
    private String seat;
}
