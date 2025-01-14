package ru.timerdar.CultureBooking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import ru.timerdar.CultureBooking.model.Event;

import java.util.ArrayList;
import java.util.Optional;


public interface EventRepository extends BaseRepository<Event, Long> {
    @Query(value = "select * from Events e", nativeQuery = true)
    ArrayList<Event> getEvents(Sort sort);
}
