package ru.timerdar.CultureBooking.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.exceptions.EmptyResultException;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.repository.EventRepository;
import ru.timerdar.CultureBooking.repository.TicketRepository;
import ru.timerdar.CultureBooking.dto.MessageResponse;
import ru.timerdar.CultureBooking.service.EventService;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController{

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<ShortEventDto> createEvent(@RequestBody EventCreationDto event) throws IllegalArgumentException{
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.created(URI.create("/api/events/" + createdEvent.getId())).body(createdEvent.toShort());
    }

    @GetMapping
    public ResponseEntity<ArrayList<ShortEventDto>> getEventsList() throws EmptyResultException {
        return ResponseEntity.ok(eventService.getEventsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) throws EmptyResultException{
        return ResponseEntity.ok(eventService.getFullEvent(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> changeEvent(@RequestBody Event event){
        return ResponseEntity.ok(eventService.changeEvent(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new MessageResponse("Мероприятие c id = " + id + " удалено"));
    }

}
