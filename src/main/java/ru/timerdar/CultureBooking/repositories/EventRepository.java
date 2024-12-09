package ru.timerdar.CultureBooking.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.timerdar.CultureBooking.entities.Event;
import ru.timerdar.CultureBooking.entities.LiteEvent;

import java.util.List;
import java.util.Map;


public interface EventRepository extends BaseRepository<Event, Long> {

    @Query(value = "select array_agg(section->>'name')" +
            " from Events, jsonb_array_elements(seats) as section" +
            " where id = :eventId ", nativeQuery = true)
    String getEventCategories(@Param("eventId") Long id);

    @Query(value = "select json_build_array(elem)" +
            " from Events e," +
            " jsonb_array_elements(seats) as elem" +
            " where e.id = :eventId and elem->> 'name' = :secName", nativeQuery = true)
    String getCategoryByNameAndEventId(@Param("eventId") Long id, @Param("secName") String sectionName);

    @Query(value = "select seats" +
            " from Events e" +
            " where e.id = :eventId", nativeQuery = true)
    String getAllSeats(@Param("eventId") Long id);

    @Query("select new ru.timerdar.CultureBooking.entities.LiteEvent(e.id, e.name, e.description, e.eventDate) from Event e")
    List<LiteEvent> findAllLiteEvents(Sort sort);

    @Modifying
    @Query(value = "update Events e " +
            "set seats = :newSeats " +
            "where e.id = :eventId", nativeQuery = true)
    void setNewSeatsObj(@Param("eventId") Long id, @Param("newSeats")List<Map<String, Object>> newSeats);
}
