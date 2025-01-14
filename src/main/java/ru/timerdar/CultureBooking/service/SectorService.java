package ru.timerdar.CultureBooking.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.SectorCreationDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.repository.SectorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public Sector createSector(SectorCreationDto newSector){
        if(newSector.isValid()){
            return sectorRepository.save(new Sector(1L, newSector.getName(), newSector.getColor(), newSector.getEventId()));
        }else{
            throw new IllegalArgumentException("Цвет сектора должен быть в формате hex, имя не должно быть пустым");
        }
    }

    public Sector getById(Long id){
        return sectorRepository.getReferenceById(id);
    }

    public List<Sector> getSectorsListOfEvent(Long eventId){
        return sectorRepository.findSectorsOfEvent(eventId);
    }
}
