package ru.timerdar.CultureBooking.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.timerdar.CultureBooking.model.Admin;

@RepositoryRestResource(exported = false)
public interface AdminRepository extends org.springframework.data.jpa.repository.JpaRepository<Admin, Long> {
}
