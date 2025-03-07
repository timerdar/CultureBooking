package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Table(name = "posters")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eventId", nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private byte[] imageData;
}
