package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Seat;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> getBySectorId(Long sectorId);

    @Modifying
    @Query("update Seat set reserved = true where id = ?1")
    int setReservedById(Long id);

    @Modifying
    @Query("update Seat set reserved = false where id = ?1")
    int setUneservedById(Long id);
}
