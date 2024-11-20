package ru.timerdar.CultureBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.entities.Event;
import ru.timerdar.CultureBooking.entities.Ticket;
import ru.timerdar.CultureBooking.repositories.EventRepository;
import ru.timerdar.CultureBooking.repositories.TicketRepository;
import ru.timerdar.CultureBooking.responses.MessageResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController{

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event){
        try {
            Event e = eventRepository.save(event);
            return ResponseEntity.created(URI.create("/api/events/" + e.getId())).body(e);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEventsList(){
        return ResponseEntity.ok(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventDate")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(event.get());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> changeEvent(@RequestBody Event event){
        Event ref = eventRepository.getReferenceById(event.getId());
        ref.setName(event.getName());
        ref.setDescription(event.getDescription());
        ref.setEventDate(event.getEventDate());
        ref.setSeats(event.getSeats());
        return ResponseEntity.ok(eventRepository.save(ref));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@PathVariable Long id){
        try {
            Optional<Event> ref = eventRepository.findById(id);
            if (ref.isPresent()){
                for(Ticket ticket: ticketRepository.findAllByEvent(ref.get())){
                    ticketRepository.delete(ticket);
                }
                eventRepository.delete(ref.get());
                return ResponseEntity.ok(new MessageResponse("Мероприятие " + ref.get().getName() + " удалено"));
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Не удалось удалить мероприятие: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<?> getCategories(@PathVariable Long id){
        if (!eventRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        List<String> categories = eventRepository.getEventCategories(id);
        if (categories.isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Категории не заполнены. Обратитесь к Администратору"));
        }else{
            return ResponseEntity.ok(categories);
        }
    }
}
