package ru.timerdar.CultureBooking.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.exceptions.EmptyResultException;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.repository.EventRepository;
import ru.timerdar.CultureBooking.repository.TicketRepository;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventCreationDto rawEvent){
        if(rawEvent.isValid()){
            throw new IllegalArgumentException("Название, описание и сектора не должны быть пустыми," +
                    " а дата проведения должна быть в будущем");
        }else{
            return eventRepository.save(rawEvent.toEvent());
        }
    }

    public ArrayList<ShortEventDto> getEventsList() throws EmptyResultException {
        Optional<ArrayList<Event>> fullEvents = eventRepository.getEvents(Sort.by(Sort.Direction.DESC, "eventDate"));
        if (fullEvents.isEmpty()){
            throw new EmptyResultException("Список мероприятий пуст");
        }
        ArrayList<ShortEventDto> resultShortEvents = new ArrayList<>();
        fullEvents.get().forEach(event -> resultShortEvents.add(event.toShort()));
        return resultShortEvents;
    }

    public Event getFullEvent(Long id) throws EmptyResultException{
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            return event.get();
        }else{
            throw new EmptyResultException("Мероприятие не найдено");
        }
    }

    public Event changeEvent(Event event){
        //TODO
        return null;
    }

    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }
}
