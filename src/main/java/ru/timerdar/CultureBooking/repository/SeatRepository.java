package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.Query;
import ru.timerdar.CultureBooking.model.Seat;

public interface SeatRepository extends BaseRepository<Seat, Long> {
    @Query("update Seat set reserved = true where id = ?1")
    int setReservedById(Long id);
}
