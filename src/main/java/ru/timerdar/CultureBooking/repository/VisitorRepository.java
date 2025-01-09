package ru.timerdar.CultureBooking.repository;

import ru.timerdar.CultureBooking.model.Visitor;

import java.util.Optional;

public interface VisitorRepository extends BaseRepository<Visitor, Long> {
    Optional<Visitor> findByNameAndSurnameAndFathernameAndCategory(String name, String surname, String fathername, String category);
}
