package ru.timerdar.CultureBooking.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.timerdar.CultureBooking.entities.Event;

import java.util.List;

public interface EventRepository extends BaseRepository<Event, Long> {

    @Query("select jsonb_object_keys(seats) from Event where id =?1 ")
    List<String> getEventCategories(Long id);
}
