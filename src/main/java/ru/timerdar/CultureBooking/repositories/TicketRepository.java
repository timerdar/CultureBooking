package ru.timerdar.CultureBooking.repositories;

import ru.timerdar.CultureBooking.entities.Event;
import ru.timerdar.CultureBooking.entities.Ticket;
import ru.timerdar.CultureBooking.entities.Visitor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends BaseRepository<Ticket, Long> {
    Optional<Ticket> findByUuid(UUID uuid);
    Optional<Ticket> findByVisitorAndEvent(Visitor visitor, Event event);
    List<Ticket> findAllByEvent(Event event);
}
