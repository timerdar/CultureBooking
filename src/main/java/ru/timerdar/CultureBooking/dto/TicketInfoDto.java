package ru.timerdar.CultureBooking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
public class TicketInfoDto {

    private UUID uuid;
    private VisitorCreationDto visitor;
    private ShortEventDto event;
    private TicketStatus status;

}
