package ru.timerdar.CultureBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;
import ru.timerdar.CultureBooking.repository.TicketRepository;

import java.time.LocalDateTime;

public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private EventService eventService;

    public Ticket createTicket(TicketCreationDto rawTicketData){
        Visitor newVisitor = visitorService.createOrUseExistingVisitor(rawTicketData.getVisitor().toVisitor());
        Seat selectedSeat = seatService.getById(rawTicketData.getSeatId());
        Event selectedEvent = eventService.getFullEvent(rawTicketData.getEventId());
        if (!selectedSeat.isReserved()){
            Ticket newTicket = new Ticket(null, selectedEvent, newVisitor, selectedSeat, TicketStatus.CREATED, LocalDateTime.now());
            ticketRepository.save(newTicket);
        }else{
            throw new //TODO Бизнесовое исключение по;
        }
    }

}
