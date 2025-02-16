package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByUuid(UUID uuid);

    Optional<Ticket> findByVisitorIdAndEventId(Long visitorId, Long eventId);

    @Query(value = "select * from Tickets where event_id = ?1 and (ticket_status = 'CREATED' or ticket_status = 'USED')", nativeQuery = true)
    List<Ticket> findAllByEventId(Long eventId);

    List<Ticket> findAllByEventIdAndSectorId(Long eventId, Long sectorId);

    List<Ticket> findAllByVisitorId(Long visitorId);

    void deleteByEventId(Long eventId);

    @Modifying
    @Query("update Ticket set ticketStatus=?2 where uuid=?1")
    int updateTicketStatusById(UUID uuid, TicketStatus status);

}