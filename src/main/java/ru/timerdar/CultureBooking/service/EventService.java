package ru.timerdar.CultureBooking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timerdar.CultureBooking.dto.EventCreationDto;
import ru.timerdar.CultureBooking.dto.SeatStatDto;
import ru.timerdar.CultureBooking.dto.ShortEventDto;
import ru.timerdar.CultureBooking.model.*;
import ru.timerdar.CultureBooking.repository.EventRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private AdminService adminService;


    @Transactional
    public Event createEvent(EventCreationDto rawEvent){
        if(rawEvent.isValid() && adminService.exists(rawEvent.getAdminId())){
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
        ArrayList<Event> fullEvents = this.getAllEventsList();
        ArrayList<ShortEventDto> resultShortEvents = new ArrayList<>();
        fullEvents.forEach(event -> {
            if (event.isVisible()) {
                resultShortEvents.add(event.toShort());
            }});
        return resultShortEvents;
    }

    public ArrayList<Event> getAllEventsList(){
        ArrayList<Event> fullEvents = eventRepository.getEvents(Sort.by(Sort.Direction.DESC, "date"));
        if (fullEvents.isEmpty()){
            throw new EntityNotFoundException("Список мероприятий пуст");
        }
        return fullEvents;
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

    public void hideEvent(Long id){
        eventRepository.hideEvent(id);
    }

    public void showEvent(Long id){
        eventRepository.showEvent(id);
    }

    public List<Sector> getSectorsOfEvent(Long id){
        List<Sector> sectors = sectorService.getSectorsListOfEvent(id);
        if (sectors.isEmpty()){
            throw new EntityNotFoundException("Для мероприятия нет заданных секторов");
        }
        return sectors;
    }

    public Sector getSector(Long eventId, Long sectorId){
        for (Sector sector:getSectorsOfEvent(eventId)){
            if (sector.getId().equals(sectorId)){
                return sector;
            }
        }
        throw new EntityNotFoundException("Сектор не найден");
    }

    public Seat getSeat(Long eventId, Long sectorId, Long seatId){
        for(Seat seat: getSeatsBySectorOfEvent(eventId, sectorId)){
            if (seat.getId().equals(seatId)){
                return seat;
            }
        }
        throw new EntityNotFoundException("Место не найдено");
    }

    public List<Seat> getSeatsBySectorOfEvent(Long eventId, Long sectorId){
        List<Sector> sectorsOfEvent = getSectorsOfEvent(eventId);
        for (Sector sector: sectorsOfEvent){
            if (sector.getId().equals(sectorId)){
                return seatService.getSeatsBySectorId(sectorId);
            }
        }
        throw new EntityNotFoundException("Данный сектор не относится к заданному мероприятию");
    }

    public SeatStatDto getSeatsStatOfEvent(Long eventId){
        int reserved = 0;
        int free = 0;
        for (Sector sector: getSectorsOfEvent(eventId)){
            for (Seat seat: getSeatsBySectorOfEvent(eventId, sector.getId())){
                if (seat.isReserved()){
                    reserved++;
                }else{
                    free++;
                }
            }
        }
        return new SeatStatDto(reserved, free);
    }
}
