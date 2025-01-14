package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.Query;
import ru.timerdar.CultureBooking.model.Sector;

import java.util.List;


public interface SectorRepository extends BaseRepository<Sector, Long>{
    @Query(value = "select * from Sector where eventId = ?1", nativeQuery = true)
    List<Sector> findSectorsOfEvent(Long eventId);
}
