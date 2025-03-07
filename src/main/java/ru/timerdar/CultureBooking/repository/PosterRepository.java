package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Poster;

@RepositoryRestResource(exported = false)
public interface PosterRepository extends JpaRepository<Poster, Long> {

    @Modifying
    @Query(value = "update Poster set imageData = ?2 where eventId = ?1")
    void updatePoster(Long id, byte[] image);

    boolean existsByEventId(Long eventId);
}
