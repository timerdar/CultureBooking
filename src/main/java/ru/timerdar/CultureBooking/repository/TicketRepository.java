package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.Query;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends BaseRepository<Ticket, UUID> {
    Optional<Ticket> findByUuid(UUID uuid);
    Optional<Ticket> findByVisitorAndEvent(Long visitorId, Long eventId);
    List<Ticket> findAllByEventId(Long eventId);
    List<Ticket> findAllByEventIdAndSectorId(Long eventId, Long sectorId);
    List<Ticket> findAllByVisitorId(Long visitorId);

    @Query("update Ticket set ticketStatus=?1 where uuid=?2")
    int updateTicketStatusById(UUID uuid, TicketStatus status);

}
