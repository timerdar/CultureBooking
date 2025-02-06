package ru.timerdar.CultureBooking.dto;

import lombok.Getter;
import lombok.Setter;
import ru.timerdar.CultureBooking.model.Visitor;

@Getter @Setter
public class VisitorCreationDto {
    private String name;
    private String surname;
    private String fathername;

    public Visitor toVisitor(){
        return new Visitor(null, this.name, this.surname, this.fathername);
    }
}
