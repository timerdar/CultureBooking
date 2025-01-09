package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.TicketDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.repository.EventRepository;
import ru.timerdar.CultureBooking.repository.TicketRepository;
import ru.timerdar.CultureBooking.repository.VisitorRepository;
import ru.timerdar.CultureBooking.dto.MessageResponse;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticket){

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTicketByUUID(@PathVariable UUID uuid){
        Optional<Ticket> ticket =  ticketRepository.findByUuid(uuid);
        if(ticket.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(ticket.get());
        }
    }


    @GetMapping(value = "/generate/qr/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getTicketQr(@PathVariable UUID uuid){
        try{
            QrGenerator qrGenerator = new QrGenerator(uri);
            byte[] image = qrGenerator.generateTicketQrImage(uuid);
            return ResponseEntity.ok(image);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}