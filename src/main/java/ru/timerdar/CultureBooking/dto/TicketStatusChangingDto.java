package ru.timerdar.CultureBooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TicketStatusChangingDto {
    private UUID ticketUUID;
    private TicketStatus newStatus;
}
