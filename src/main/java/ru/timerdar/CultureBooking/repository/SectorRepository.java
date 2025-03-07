package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Sector;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SectorRepository extends JpaRepository<Sector, Long> {
    @Query(value = "select * from Sectors where event_id = ?1", nativeQuery = true)
    List<Sector> findSectorsOfEvent(Long eventId);
}
