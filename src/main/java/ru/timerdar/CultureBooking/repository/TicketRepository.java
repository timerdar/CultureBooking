package ru.timerdar.CultureBooking.repository;

import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends BaseRepository<Ticket, Long> {
    Optional<Ticket> findByUuid(UUID uuid);
    Optional<Ticket> findByVisitorAndEvent(Visitor visitor, Event event);
    List<Ticket> findAllByEvent(Event event);
}
