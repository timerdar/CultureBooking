package ru.timerdar.CultureBooking.repositories;

import ru.timerdar.CultureBooking.entities.Visitor;

import java.util.Optional;

public interface VisitorRepository extends BaseRepository<Visitor, Long> {
    Optional<Visitor> findByNameAndSurnameAndFathernameAndCategory(String name, String surname, String fathername, String category);
}
