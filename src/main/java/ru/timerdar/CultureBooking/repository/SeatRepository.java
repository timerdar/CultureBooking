package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Seat;

@RepositoryRestResource(exported = false)
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("update Seat set reserved = true where id = ?1")
    int setReservedById(Long id);
}
