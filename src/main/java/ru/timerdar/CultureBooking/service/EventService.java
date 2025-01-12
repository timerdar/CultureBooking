package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.repository.EventRepository;

import java.util.ArrayList;
import java.util.Optional;

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
            rawEvent.getSectors().forEach(newSector -> {
                newSector.getSeats().forEach(newSeat -> seatService.createSeat(newSeat));
                sectorService.createSector(newSector);
            });
            return eventRepository.save(rawEvent.toEvent());
        }else{
            throw new IllegalArgumentException("Название, описание и сектора не должны быть пустыми," +
                    " а дата проведения должна быть в будущем");
        }
    }

    public ArrayList<ShortEventDto> getEventsList(){
        Optional<ArrayList<Event>> fullEvents = eventRepository.getEvents(Sort.by(Sort.Direction.DESC, "eventDate"));
        if (fullEvents.isEmpty()){
            throw new EntityNotFoundException("Список мероприятий пуст");
        }
        ArrayList<ShortEventDto> resultShortEvents = new ArrayList<>();
        fullEvents.get().forEach(event -> resultShortEvents.add(event.toShort()));
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
