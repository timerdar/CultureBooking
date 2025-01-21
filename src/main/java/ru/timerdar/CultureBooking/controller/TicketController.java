package ru.timerdar.CultureBooking.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.exceptions.TicketStatusChangingException;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.dto.MessageResponse;
import ru.timerdar.CultureBooking.service.QrGenerationService;
import ru.timerdar.CultureBooking.service.TicketService;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Value("${ru.timerdar.ticket.uri}")
    String QR_URI;

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
            byte[] image = QrGenerationService.generateTicketQrImage(uuid, QR_URI);
            return ResponseEntity.ok(image);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    //admin_permission
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/ban")
    public ResponseEntity<?> banTicket(@RequestParam UUID uuid) throws TicketStatusChangingException {
        return ResponseEntity.ok(ticketService.banTicketByUuid(uuid));
    }

    @PostMapping("/{uuid}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable UUID uuid) throws TicketStatusChangingException {
        return ResponseEntity.ok(ticketService.cancelTicket(uuid));
    }

    @PostMapping("/{uuid}/check")
    public ResponseEntity<?> checkTicketOnEnter(@PathVariable UUID uuid) throws TicketStatusChangingException {
        return ResponseEntity.ok(ticketService.checkTicketOnEnter(uuid));
    }
}