package ru.timerdar.CultureBooking.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.timerdar.CultureBooking.entities.Event;
import ru.timerdar.CultureBooking.entities.LiteEvent;

import java.util.List;

public interface EventRepository extends BaseRepository<Event, Long> {

    @Query(value = "select array_agg(section->>'name')" +
            " from Events, jsonb_array_elements(seats) as section" +
            " where id = :eventId ", nativeQuery = true)
    String getEventCategories(@Param("eventId") Long id);

    @Query("select new ru.timerdar.CultureBooking.entities.LiteEvent(e.id, e.name, e.description, e.eventDate) from Event e")
    List<LiteEvent> findAllLiteEvents(Sort sort);
}
