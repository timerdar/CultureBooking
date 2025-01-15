package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByUuid(UUID uuid);
    Optional<Ticket> findByVisitorIdAndEventId(Long visitorId, Long eventId);
    List<Ticket> findAllByEventId(Long eventId);
    List<Ticket> findAllByEventIdAndSectorId(Long eventId, Long sectorId);
    List<Ticket> findAllByVisitorId(Long visitorId);

    @Query("update Ticket set ticketStatus=?1 where uuid=?2")
    int updateTicketStatusById(UUID uuid, TicketStatus status);

}
