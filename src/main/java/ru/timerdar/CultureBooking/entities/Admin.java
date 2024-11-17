package ru.timerdar.CultureBooking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "admins")
public class Admin {
    @Id
    private String login;

    @Column(nullable = false)
    private String password;
}
