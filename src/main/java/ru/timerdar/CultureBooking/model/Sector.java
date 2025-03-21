package ru.timerdar.CultureBooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Long eventId;


    @JsonIgnore
    public boolean isValid(){
        return !(color.matches("#\\d{6}") || name.isEmpty());
    }
}
