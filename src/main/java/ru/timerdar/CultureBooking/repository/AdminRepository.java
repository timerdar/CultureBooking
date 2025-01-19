package ru.timerdar.CultureBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Admin;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> getByUsername(String username);

}
