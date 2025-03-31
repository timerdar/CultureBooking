package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.timerdar.CultureBooking.model.Visitor;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByNameAndSurnameAndFathername(String name, String surname, String fathername);
    boolean existsByNameAndSurnameAndFathername(String name, String surname, String fathername);
    Optional<Visitor> findByEmail(String email);
}
