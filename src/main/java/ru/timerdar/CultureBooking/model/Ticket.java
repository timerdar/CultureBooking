package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ru.timerdar.CultureBooking.model.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Long visitorId;

    @Column(nullable = false)
    private Long sectorId;

    @Column(nullable = false)
    private Long seatId;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private LocalDateTime created;
}