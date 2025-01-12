package ru.timerdar.CultureBooking.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.timerdar.CultureBooking.dto.SeatCreationDto;
import ru.timerdar.CultureBooking.model.Seat;
import ru.timerdar.CultureBooking.repository.SeatRepository;

@AllArgsConstructor
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat createSeat(SeatCreationDto newSeat){
        if(newSeat.isValid()){
            return seatRepository.save(newSeat.toSeat());
        }else{
            throw new IllegalArgumentException("Номер должен быть в формате <номер ряда>-<номер места>");
        }
    }

    public void reserveById(Long id){
        seatRepository.setReservedById(id);
    }

    public Seat getById(Long id){
        return seatRepository.getReferenceById(id);
    }

}
