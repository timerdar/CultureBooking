package ru.timerdar.CultureBooking.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.repository.VisitorRepository;

import java.util.Optional;

@AllArgsConstructor
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    public Visitor createOrUseExistingVisitor(Visitor visitor){
        Optional<Visitor> existingVisitor = visitorRepository.findByNameAndSurnameAndFathername(visitor.getName(), visitor.getSurname(), visitor.getFathername());
        return existingVisitor.orElseGet(() -> visitorRepository.save(visitor));
    }
}
