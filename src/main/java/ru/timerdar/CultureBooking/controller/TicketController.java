package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.repository.EventRepository;
import ru.timerdar.CultureBooking.repository.TicketRepository;
import ru.timerdar.CultureBooking.repository.VisitorRepository;
import ru.timerdar.CultureBooking.dto.MessageResponse;
import ru.timerdar.CultureBooking.service.QrGenerationService;
import ru.timerdar.CultureBooking.service.TicketService;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Value("${ru.timerdar.ticket.uri}")
    String qr_uri;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketCreationDto rawTicket) throws TicketReservationException {
        Ticket newTicket = ticketService.createTicket(rawTicket);
        return ResponseEntity.created(URI.create("/api/tickets/" + newTicket.getUuid())).body(newTicket);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTicketByUUID(@PathVariable UUID uuid){
        return ResponseEntity.ok(ticketService.getByUUID(uuid));
    }


    @GetMapping(value = "/generate/qr/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getTicketQr(@PathVariable UUID uuid){

        try{
            byte[] image = QrGenerationService.generateTicketQrImage(uuid, qr_uri);
            return ResponseEntity.ok(image);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}