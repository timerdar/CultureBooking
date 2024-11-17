package ru.timerdar.CultureBooking.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "tickets")
public class Ticket {

    @ManyToOne
    @JoinColumn(name = "visitorId", referencedColumnName = "id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "id")
    private Event event;

    @Column(nullable = false)
    private String seat;

    @Id
    @UuidGenerator
    private UUID uuid;
}
