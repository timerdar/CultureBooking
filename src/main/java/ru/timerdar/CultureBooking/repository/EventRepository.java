package ru.timerdar.CultureBooking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Event;

import java.util.ArrayList;

@RepositoryRestResource(exported = false)
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select * from Events e", nativeQuery = true)
    ArrayList<Event> getEvents(Sort sort);

    @Modifying
    @Query(value = "update Events set visible = false where id = ?1", nativeQuery = true)
    void hideEvent(Long id);

    @Modifying
    @Query(value = "update Events set visible = true where id = ?1", nativeQuery = true)
    void showEvent(Long id);
}
