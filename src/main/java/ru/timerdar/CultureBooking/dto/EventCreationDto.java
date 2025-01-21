package ru.timerdar.CultureBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.timerdar.CultureBooking.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventCreationDto {
    private String name;
    private String description;
    private Long adminId;
    private LocalDateTime date;
    private List<SectorCreationDto> sectors;

    @JsonIgnore
    public boolean isValid(){
        return !(this.name.isEmpty() || this.description.isEmpty() || this.date.isBefore(LocalDateTime.now()) || this.sectors.isEmpty());
    }

    public Event toEvent(){
        return new Event(1L, this.name, this.description, this.date, LocalDateTime.now(), this.adminId);
    }
}
