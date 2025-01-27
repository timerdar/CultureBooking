package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Poster;

@RepositoryRestResource(exported = false)
public interface PosterRepository extends JpaRepository<Poster, Long> {
}
