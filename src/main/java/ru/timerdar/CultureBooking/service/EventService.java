package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.repository.EventRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SectorService sectorService;

    public Event createEvent(EventCreationDto rawEvent){
        if(rawEvent.isValid()){
            Event createdEvent = eventRepository.save(rawEvent.toEvent());
            rawEvent.getSectors().forEach(newSector -> {
                Sector createdSector = sectorService.createSector(newSector, createdEvent.getId());
                newSector.getSeats().forEach(newSeat -> seatService.createSeat(newSeat, createdSector.getId()));
            });
            return createdEvent;
        }else{
            throw new IllegalArgumentException("Название, описание и сектора не должны быть пустыми," +
                    " а дата проведения должна быть в будущем");
        }
    }

    public ArrayList<ShortEventDto> getEventsList(){
        ArrayList<Event> fullEvents = eventRepository.getEvents(Sort.by(Sort.Direction.DESC, "date"));
        if (fullEvents.isEmpty()){
            throw new EntityNotFoundException("Список мероприятий пуст");
        }
        ArrayList<ShortEventDto> resultShortEvents = new ArrayList<>();
        fullEvents.forEach(event -> resultShortEvents.add(event.toShort()));
        return resultShortEvents;
    }

    public Event getFullEvent(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            return event.get();
        }else{
            throw new EntityNotFoundException("Мероприятие не найдено");
        }
    }

    public Event changeEvent(Event event){
        if(eventRepository.existsById(event.getId())){
            return eventRepository.save(event);
        }else{
            throw new EntityNotFoundException("Мероприятие не существует");
        }
    }

    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }
}
