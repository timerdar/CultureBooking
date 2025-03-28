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

    @Column(nullable = false)
    private String email;

    @Override
    public String toString() {
        return surname + " " + name + " " + fathername + " " + email;
    }

    public VisitorCreationDto toDto(){
        VisitorCreationDto dto = new VisitorCreationDto();
        dto.setFathername(fathername);
        dto.setName(name);
        dto.setSurname(surname);
        dto.setEmail(email);
        return dto;
    }
}