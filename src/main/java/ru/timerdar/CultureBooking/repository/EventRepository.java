package ru.timerdar.CultureBooking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Event;

import java.util.ArrayList;

@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select * from Events e", nativeQuery = true)
    ArrayList<Event> getEvents(Sort sort);
}
