package ru.timerdar.CultureBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.entities.Event;
import ru.timerdar.CultureBooking.entities.Ticket;
import ru.timerdar.CultureBooking.entities.Visitor;
import ru.timerdar.CultureBooking.repositories.EventRepository;
import ru.timerdar.CultureBooking.repositories.TicketRepository;
import ru.timerdar.CultureBooking.repositories.VisitorRepository;
import ru.timerdar.CultureBooking.requests.TicketRequest;
import ru.timerdar.CultureBooking.responses.MessageResponse;
import ru.timerdar.CultureBooking.ticketCreation.QrGenerator;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VisitorRepository visitorRepository;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest ticket){
            try{
                Optional<Visitor> visitor = visitorRepository.findById(ticket.getVisitorId());
                Optional<Event> event = eventRepository.findById(ticket.getEventId());
                if (visitor.isEmpty() || event.isEmpty()){
                    return ResponseEntity.notFound().build();
                }
                Optional<Ticket> alreadyCreated = ticketRepository.findByVisitorAndEvent(
                        visitor.get(),
                        event.get()
                );
                if(alreadyCreated.isEmpty()) {
                    Ticket newTicket = new Ticket();
                    newTicket.setSeat(ticket.getSeat());
                    newTicket.setEvent(event.get());
                    newTicket.setVisitor(visitor.get());
                    Ticket t = ticketRepository.save(newTicket);
                    return ResponseEntity.created(URI.create("/api/tickets/" + t.getUuid())).body(t);
                }else {
                    return ResponseEntity.ok(alreadyCreated.get());
                }
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            }

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
    public ResponseEntity<?> getTicketQr(@PathVariable UUID uuid, @Value("${ru.timerdar.ticket.uri}") String uri){
        try{
            QrGenerator qrGenerator = new QrGenerator(uri);
            byte[] image = qrGenerator.generateTicketQrImage(uuid);
            return ResponseEntity.ok(image);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}