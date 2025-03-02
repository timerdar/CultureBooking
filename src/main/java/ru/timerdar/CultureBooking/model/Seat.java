package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rowAndSeatNumber;

    @Column(nullable = false)
    private boolean reserved;

    @Column(nullable = false)
    private Long sectorId;
}
