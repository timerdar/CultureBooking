package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.EventAndSectorIdsDto;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.dto.TicketStatusChangingDto;
import ru.timerdar.CultureBooking.exceptions.TicketCheckingException;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.model.*;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;
import ru.timerdar.CultureBooking.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;

    @Value("${ru.timerdar.ticket.timeToCheck}")
    private int timeToStartTicketChecking;

    public Ticket createTicket(TicketCreationDto rawTicketData) throws TicketReservationException {
        Visitor newVisitor = visitorService.createOrUseExistingVisitor(rawTicketData.getVisitor().toVisitor());
        Seat selectedSeat = seatService.getById(rawTicketData.getSeatId());
        if (!selectedSeat.isReserved()){
            seatService.reserveById(rawTicketData.getSeatId());
            Ticket newTicket = new Ticket(null, rawTicketData.getEventId(), newVisitor.getId(), rawTicketData.getSeatId(), rawTicketData.getSectorId(), TicketStatus.CREATED, LocalDateTime.now());
            return ticketRepository.save(newTicket);
        }else{
            throw new TicketReservationException("Данное место уже занято, выберите другое");
        }
    }

    public Ticket banTicketByUuid(UUID ticketUuid){
        TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.BANNED);
        Ticket bannedTicket = this.changeTicketStatus(changingDto);
        seatService.unreserveById(bannedTicket.getSeatId());
        return bannedTicket;
    }

    public Ticket cancelTicket(UUID ticketUuid){
        TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.CANCELED);
        Ticket canceledTicket = this.changeTicketStatus(changingDto);
        seatService.unreserveById(canceledTicket.getSeatId());
        return canceledTicket;
    }

    public Ticket checkTicketOnEnter(UUID ticketUuid) throws TicketCheckingException{
        Optional<Ticket> ticket = ticketRepository.findByUuid(ticketUuid);
        if (ticket.isPresent()){
            Event event = eventService.getFullEvent(ticket.get().getEventId());
            LocalDateTime ticketCheckTime = event.getDate().minusMinutes(timeToStartTicketChecking);
            if(LocalDateTime.now().isBefore(ticketCheckTime)){
                TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.USED);
                return this.changeTicketStatus(changingDto);
            }else{
                throw new TicketCheckingException("Подтвердить присутствие необходимо не раньше, чем за " + ticketCheckTime + " минут до начала мероприятия");
            }
        }else{
            throw new EntityNotFoundException("Билет не найден");
        }

    }

    public Ticket changeTicketStatus(TicketStatusChangingDto ticketStatusChanging){
        Ticket ticket = getByUUID(ticketStatusChanging.getTicketUUID());
        ticketRepository.updateTicketStatusById(ticketStatusChanging.getTicketUUID(), ticketStatusChanging.getNewStatus());
        return ticketRepository.getReferenceById(ticketStatusChanging.getTicketUUID());
    }

    public List<Ticket> getAllTicketsByEvent(Long eventId){
        return ticketRepository.findAllByEventId(eventId);
    }

    public List<Ticket> getAllTicketsByEventAndSector(EventAndSectorIdsDto idsDto){
        return ticketRepository.findAllByEventIdAndSectorId(idsDto.getEventId(), idsDto.getSectorId());
    }

    public List<Ticket> getAllTicketsByVisitor(Long visitorId){
        return ticketRepository.findAllByVisitorId(visitorId);
    }

    public Ticket getByUUID(UUID uuid){
        Optional<Ticket> ticket = ticketRepository.findByUuid(uuid);
        if (ticket.isEmpty()) {
            throw new EntityNotFoundException("Билет не найден");
        }else{
            return ticket.get();
        }
    }
}
