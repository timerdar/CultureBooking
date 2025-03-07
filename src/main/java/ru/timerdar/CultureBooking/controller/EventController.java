package ru.timerdar.CultureBooking.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.timerdar.CultureBooking.dto.*;
import ru.timerdar.CultureBooking.handler.RestResponsesExceptionsHandler;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Poster;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.service.EventService;
import ru.timerdar.CultureBooking.service.PosterService;
import ru.timerdar.CultureBooking.service.TicketService;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventController{

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private PosterService posterService;

    @Autowired
    private TicketService ticketService;


    //admin_permission
    @SecurityRequirement(name = "Authorization")
    @PostMapping
    public ResponseEntity<ShortEventDto> createEvent(@RequestBody EventCreationDto event) throws IllegalArgumentException{
        Event createdEvent = eventService.createEvent(event);
        log.info("Создано мероприятие: " + createdEvent.getId());
        return ResponseEntity.created(URI.create("/api/events/" + createdEvent.getId())).body(createdEvent.toShort());
    }

    @GetMapping
    public ResponseEntity<ArrayList<ShortEventDto>> getVisibleEventsList(){
        return ResponseEntity.ok(eventService.getEventsList());
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Event>> getAllEventsList(){
        return ResponseEntity.ok(eventService.getAllEventsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        return ResponseEntity.ok(eventService.getFullEvent(id));
    }

    //admin_permission
    @SecurityRequirement(name = "Authorization")
    @PatchMapping("/{id}")
    public ResponseEntity<Event> changeEvent(@RequestBody Event event){
        return ResponseEntity.ok(eventService.changeEvent(event));
    }

    //admin_permission
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        ticketService.deleteByEventId(id);
        log.info("Мероприятие c id = " + id + " удалено");
        return ResponseEntity.ok(new MessageResponse("Мероприятие c id = " + id + " удалено"));
    }

    @Transactional
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{id}/hide")
    public ResponseEntity<MessageResponse> hideEvent(@PathVariable Long id){
        eventService.hideEvent(id);
        log.info("Мероприятие c id = " + id + " скрыто");
        return ResponseEntity.ok(new MessageResponse("Мероприятие с id = " + id + " скрыто"));
    }

    @Transactional
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/{id}/show")
    public ResponseEntity<MessageResponse> showEvent(@PathVariable Long id){
        eventService.showEvent(id);
        log.info("Мероприятие c id = " + id + " открыто");
        return ResponseEntity.ok(new MessageResponse("Мероприятие с id = " + id + " открыто"));
    }

    @GetMapping("/{id}/sectors")
    public ResponseEntity<List<Sector>> getEventSectors(@PathVariable("id") Long eventId){
        return ResponseEntity.ok(eventService.getSectorsOfEvent(eventId));
    }

    @GetMapping("/{id}/{sectorId}")
    public ResponseEntity<Sector> getSector(@PathVariable("id") Long eventId, @PathVariable("sectorId") Long sectorId){
        return ResponseEntity.ok(eventService.getSector(eventId, sectorId));
    }

    @GetMapping("/{eventId}/{sectorId}/seats")
    public ResponseEntity<List<Seat>> getSeatsOfEventBySector(@PathVariable Long eventId, @PathVariable Long sectorId){
        return ResponseEntity.ok(eventService.getSeatsBySectorOfEvent(eventId, sectorId));
    }

    @GetMapping("/{eventId}/{sectorId}/{seatId}")
    public ResponseEntity<Seat> getSeatOfEvent(@PathVariable Long eventId, @PathVariable Long sectorId, @PathVariable Long seatId){
        return ResponseEntity.ok(eventService.getSeat(eventId, sectorId, seatId));
    }

    @GetMapping("/{eventId}/allSeats")
    public ResponseEntity<List<SectorWithSeatsDto>> getAllSeatsOfEvent(@PathVariable Long eventId){
        return ResponseEntity.ok(eventService.getAllSeatsOfEvent(eventId));
    }

    @GetMapping("/{eventId}/detailed-statictics")
    public ResponseEntity<List<SectorSeatsStatDto>> getDetailedStatictic(@PathVariable Long eventId){
        return ResponseEntity.ok(eventService.getStatOfEverySector(eventId));
    }

    @Transactional
    @SecurityRequirement(name = "Authorization")
    @PostMapping(value = "/poster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Poster> uploadPoster(@RequestParam Long eventId, @RequestParam("file") MultipartFile file) throws IOException{
        return ResponseEntity.ok(posterService.savePoster(eventId, file));
    }

    @GetMapping(value = "/{id}/poster", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPoster(@PathVariable Long id){
        return ResponseEntity.ok().body(posterService.getPosterOfEvent(id).getImageData());
    }

    @GetMapping("/{eventId}/statistics")
    public ResponseEntity<SeatStatDto> getStatisticOfEventSeats(@PathVariable Long eventId){
        return ResponseEntity.ok(eventService.getSeatsStatOfEvent(eventId));
    }
}
