package ru.timerdar.CultureBooking.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.SectorCreationDto;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.repository.SectorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public Sector createSector(SectorCreationDto newSector, Long eventId){
        if(newSector.isValid()){
            return sectorRepository.save(new Sector(null, newSector.getName(), newSector.getColor(), eventId));
        }else{
            throw new IllegalArgumentException("Цвет сектора должен быть в формате hex, имя не должно быть пустым");
        }
    }

    public Sector getSector(Long sectorId){
        return sectorRepository.getReferenceById(sectorId);
    }

    public List<Sector> getSectorsListOfEvent(Long eventId){
        return sectorRepository.findSectorsOfEvent(eventId);
    }
}
