package ru.timerdar.CultureBooking.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Setter @Getter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT", name = "description")
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Type(JsonType.class) // Указываем использование JSON-типа Hibernate
    @Column(columnDefinition = "jsonb") // Используем jsonb для хранения данных
    private List<Map<String, Object>> seats;

}
