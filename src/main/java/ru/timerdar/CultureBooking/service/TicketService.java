package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.dto.TicketInfoDto;
import ru.timerdar.CultureBooking.dto.TicketStatusChangingDto;
import ru.timerdar.CultureBooking.dto.VisitorCreationDto;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.exceptions.TicketStatusChangingException;
import ru.timerdar.CultureBooking.model.*;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;
import ru.timerdar.CultureBooking.repository.TicketRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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

    @Value("${ru.timerdar.ticket.img.path}")
    private String path;

    @Transactional
    public Ticket createTicket(TicketCreationDto rawTicketData) throws TicketReservationException {
        Visitor newVisitor = visitorService.createOrUseExistingVisitor(rawTicketData.getVisitor().toVisitor());
        Seat selectedSeat = seatService.getById(rawTicketData.getSeatId());
        if (!selectedSeat.isReserved()){
            seatService.reserveById(rawTicketData.getSeatId());
            Ticket newTicket = new Ticket(null, rawTicketData.getEventId(), newVisitor.getId(), rawTicketData.getSectorId(), rawTicketData.getSeatId(), TicketStatus.CREATED, LocalDateTime.now());
            return ticketRepository.save(newTicket);
        }else{
            throw new TicketReservationException("Данное место уже занято, выберите другое");
        }
    }

    @Transactional
    public Ticket banTicketByUuid(UUID ticketUuid) throws TicketStatusChangingException {
        TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.BANNED);
        Ticket bannedTicket = this.changeTicketStatus(changingDto);
        seatService.unreserveById(bannedTicket.getSeatId());
        return bannedTicket;
    }

    @Transactional
    public Ticket cancelTicket(UUID ticketUuid) throws TicketStatusChangingException {
        TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.CANCELED);
        Ticket canceledTicket = this.changeTicketStatus(changingDto);
        seatService.unreserveById(canceledTicket.getSeatId());
        return canceledTicket;
    }

    @Transactional
    public Ticket checkTicketOnEnter(UUID ticketUuid) throws TicketStatusChangingException{
        Optional<Ticket> ticket = ticketRepository.findByUuid(ticketUuid);
        if (ticket.isPresent()){
            Event event = eventService.getFullEvent(ticket.get().getEventId());
            TicketStatusChangingDto changingDto = new TicketStatusChangingDto(ticketUuid, TicketStatus.USED);
            return this.changeTicketStatus(changingDto);
        }else{
            throw new EntityNotFoundException("Билет не найден");
        }

    }


    public byte[] getPdf(UUID uuid, String uri) throws IOException {
        Ticket ticket = getByUUID(uuid);
        Event event = eventService.getFullEvent(ticket.getEventId());
        Visitor visitor = visitorService.getVisitor(ticket.getVisitorId());
        Seat seat = seatService.getById(ticket.getSeatId());
        Sector sector = sectorService.getSector(ticket.getSectorId());
        return PdfGenerationService.generateTicketPdf(ticket, uri, visitor, event, sector, seat, path);
    }


    public Ticket changeTicketStatus(TicketStatusChangingDto ticketStatusChanging) throws TicketStatusChangingException {
        Ticket ticket = getByUUID(ticketStatusChanging.getTicketUUID());
        if (ticketStatusChanging.getNewStatus() != TicketStatus.CREATED){
            if (ticket.getTicketStatus() != TicketStatus.CREATED){
                throw new TicketStatusChangingException("Заблокировать/отменить можно только созданный билет");
            }
        }
        ticketRepository.updateTicketStatusById(ticketStatusChanging.getTicketUUID(), ticketStatusChanging.getNewStatus());
        return ticketRepository.getReferenceById(ticketStatusChanging.getTicketUUID());
    }

    public List<Ticket> getAllTicketsByEvent(Long eventId){
        return ticketRepository.findAllByEventId(eventId);
    }

    public List<Ticket> getAllTicketsByEventAndSector(Long eventId, Long sectorId){
        return ticketRepository.findAllByEventIdAndSectorId(eventId, sectorId);
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

    public TicketInfoDto getInfo(UUID uuid){
        Ticket ticket = getByUUID(uuid);
        Visitor visitor = visitorService.getVisitor(ticket.getVisitorId());
        Event event = eventService.getFullEvent(ticket.getEventId());
        Sector sector = sectorService.getSector(ticket.getSectorId());
        Seat seat = seatService.getById(ticket.getSeatId());
        TicketInfoDto result = new TicketInfoDto();
        result.setEvent(event.toShort());
        result.setUuid(uuid);
        result.setVisitor(visitor.toDto());
        result.setStatus(ticket.getTicketStatus());
        result.setSeat(seat.getRowAndSeatNumber());
        result.setSector(sector.getName());
        result.setCreated(ticket.getCreated());
        result.setSectorColor(sector.getColor());
        return result;
    }

    public void deleteByEventId(Long eventId){
        ticketRepository.deleteByEventId(eventId);
    }
}
