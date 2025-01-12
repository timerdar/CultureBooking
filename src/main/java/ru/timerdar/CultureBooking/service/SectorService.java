package ru.timerdar.CultureBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.timerdar.CultureBooking.dto.SectorCreationDto;
import ru.timerdar.CultureBooking.model.Sector;
import ru.timerdar.CultureBooking.repository.SectorRepository;

public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    public Sector createSector(SectorCreationDto newSector){
        if(newSector.isValid()){
            return sectorRepository.save(newSector.toSector());
        }else{
            throw new IllegalArgumentException("Цвет сектора должен быть в формате hex, имя не должно быть пустым");
        }
    }

    public Sector getById(Long id){
        return sectorRepository.getReferenceById(id);
    }
}
