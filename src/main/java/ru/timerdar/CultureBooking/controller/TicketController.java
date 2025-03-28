package ru.timerdar.CultureBooking.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.TicketCreationDto;
import ru.timerdar.CultureBooking.dto.TicketInfoDto;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.exceptions.TicketStatusChangingException;
import ru.timerdar.CultureBooking.handler.RestResponsesExceptionsHandler;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.dto.MessageResponse;
import ru.timerdar.CultureBooking.service.PdfGenerationService;
import ru.timerdar.CultureBooking.service.QrGenerationService;
import ru.timerdar.CultureBooking.service.TicketService;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @Value("${ru.timerdar.ticket.uri}")
    String QR_URI;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketCreationDto rawTicket) throws TicketReservationException, MessagingException, IOException {
        Ticket newTicket = ticketService.createTicket(rawTicket);
        log.info("Создан билет: " + newTicket.getUuid());
        return ResponseEntity.created(URI.create("/api/tickets/" + newTicket.getUuid())).body(newTicket);
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{eventId}/byEvent")
    public ResponseEntity<List<Ticket>> getAllTicketsOfEvent(@PathVariable("eventId") Long eventId){
        return ResponseEntity.ok(ticketService.getAllTicketsByEvent(eventId));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Ticket> getTicketByUUID(@PathVariable UUID uuid){
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

    @GetMapping(value = "/generate/pdf/{uuid}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getTicketPdf(@PathVariable UUID uuid) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "ticket-" + uuid + ".pdf");

        byte[] pdf = ticketService.getPdf(uuid, QR_URI);
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    //admin_permission
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{uuid}/ban")
    public ResponseEntity<?> banTicket(@PathVariable UUID uuid) throws TicketStatusChangingException {
        ticketService.banTicketByUuid(uuid);
        log.info("Отозван билет: " + uuid);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/{uuid}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable UUID uuid) throws TicketStatusChangingException {
        ticketService.cancelTicket(uuid);
        log.info("Отменен билет: " + uuid);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/{uuid}/check")
    public ResponseEntity<?> checkTicketOnEnter(@PathVariable UUID uuid) throws TicketStatusChangingException {
        log.info("Использован билет: " + uuid);
        return ResponseEntity.ok(ticketService.checkTicketOnEnter(uuid));
    }

    @GetMapping("/{uuid}/info")
    public ResponseEntity<TicketInfoDto> getTicketInfo(@PathVariable UUID uuid){
        return ResponseEntity.ok(ticketService.getInfo(uuid));
    }
}