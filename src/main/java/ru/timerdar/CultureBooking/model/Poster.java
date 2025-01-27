package ru.timerdar.CultureBooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posters")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Poster {

    @Id
    @Column(name = "eventId", nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private byte[] imageData;
}
