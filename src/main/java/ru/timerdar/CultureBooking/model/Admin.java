package ru.timerdar.CultureBooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.dto.ShortEventDto;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "admins")
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mobilePhone;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public ShortAdminDto toShort(){
        return new ShortAdminDto(id, name, mobilePhone);
    }
}
