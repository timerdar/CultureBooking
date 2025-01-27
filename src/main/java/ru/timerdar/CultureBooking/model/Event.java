package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.dto.ShortEventDto;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT", name = "description")
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long adminId;

    @Column(nullable = false)
    private boolean visible;

    public ShortEventDto toShort(){
        return new ShortEventDto(this.id, this.name, this.description, this.date);
    }



}
