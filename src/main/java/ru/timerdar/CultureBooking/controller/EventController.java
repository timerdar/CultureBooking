package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.dto.MessageResponse;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.service.EventService;
import ru.timerdar.CultureBooking.service.SeatService;
import ru.timerdar.CultureBooking.service.SectorService;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController{

    @Autowired
    private EventService eventService;


    //admin_permission
    @PostMapping
    public ResponseEntity<ShortEventDto> createEvent(@RequestBody EventCreationDto event) throws IllegalArgumentException{
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.created(URI.create("/api/events/" + createdEvent.getId())).body(createdEvent.toShort());
    }

    @GetMapping
    public ResponseEntity<ArrayList<ShortEventDto>> getEventsList(){
        return ResponseEntity.ok(eventService.getEventsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        return ResponseEntity.ok(eventService.getFullEvent(id));
    }

    //admin_permission
    @PatchMapping("/{id}")
    public ResponseEntity<Event> changeEvent(@RequestBody Event event){
        return ResponseEntity.ok(eventService.changeEvent(event));
    }

    //admin_permission
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new MessageResponse("Мероприятие c id = " + id + " удалено"));
    }

    @GetMapping("/api/event/{id}/sectors")
    public ResponseEntity<List<Sector>> getEventSectors(@PathVariable("id") Long eventId){
        return ResponseEntity.ok(eventService.getSectorsOfEvent(eventId));
    }
}
