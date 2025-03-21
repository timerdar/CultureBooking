package ru.timerdar.CultureBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.dto.VisitorCreationDto;

@Entity
@Getter @Setter
@Table(name = "visitors")
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String fathername;

    @Override
    public String toString() {
        return surname + " " + name + " " + fathername;
    }

    public VisitorCreationDto toDto(){
        VisitorCreationDto dto = new VisitorCreationDto();
        dto.setFathername(fathername);
        dto.setName(name);
        dto.setSurname(surname);
        return dto;
    }
}