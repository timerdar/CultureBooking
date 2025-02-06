package ru.timerdar.CultureBooking.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.SeatCreationDto;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.repository.SeatRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat createSeat(SeatCreationDto newSeat, Long sectorId){
        if(newSeat.isValid()){
            return seatRepository.save(new Seat(null, newSeat.getRowAndSeatNumber(), false, sectorId));
        }else{
            throw new IllegalArgumentException("Номер должен быть в формате <номер ряда>-<номер места>");
        }
    }

    public void reserveById(Long id){
        seatRepository.setReservedById(id);
    }
    public void unreserveById(Long id) {
        seatRepository.setUneservedById(id);
    }

    public Seat getById(Long id){
        return seatRepository.getById(id);
    }

    public void delete(Long id){

        seatRepository.deleteById(id);
    }

    public List<Seat> getSeatsBySectorId(Long sectorId){
        return seatRepository.getBySectorId(sectorId);
    }
}
