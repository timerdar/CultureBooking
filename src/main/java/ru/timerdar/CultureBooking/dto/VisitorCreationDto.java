package ru.timerdar.CultureBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Visitor;

@Getter @Setter
public class VisitorCreationDto {
    private String name;
    private String surname;
    private String fathername;
    private String email;

    public Visitor toVisitor(){
        return new Visitor(null, this.name, this.surname, this.fathername, this.email);
    }
    @JsonIgnore
    public String getFio(){
        return this.surname + " " + this.name + " " + this.fathername;
    }
}
