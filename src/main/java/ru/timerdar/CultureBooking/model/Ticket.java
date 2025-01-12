package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "tickets")
@AllArgsConstructor
public class Ticket {

    @Id
    @UuidGenerator
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "visitorId", referencedColumnName = "id")
    private Visitor visitor;

    @OneToOne
    @JoinColumn(name = "seatId", referencedColumnName = "id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private LocalDateTime created;
}