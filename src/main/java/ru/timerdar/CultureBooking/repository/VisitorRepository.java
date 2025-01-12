package ru.timerdar.CultureBooking.repository;

import org.springframework.data.domain.Example;
import ru.timerdar.CultureBooking.model.Visitor;

import java.util.Optional;

public interface VisitorRepository extends BaseRepository<Visitor, Long> {
    Optional<Visitor> findByNameAndSurnameAndFathername(String name, String surname, String fathername);

    boolean existsByNameAndSurnameAndFathername(String name, String surname, String fathername);
}
