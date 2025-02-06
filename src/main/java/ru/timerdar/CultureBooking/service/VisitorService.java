package ru.timerdar.CultureBooking.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.repository.VisitorRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    public Visitor createOrUseExistingVisitor(Visitor visitor){
        Optional<Visitor> existingVisitor = visitorRepository.findByNameAndSurnameAndFathername(visitor.getName(), visitor.getSurname(), visitor.getFathername());
        return existingVisitor.orElseGet(() -> visitorRepository.save(visitor));
    }

    public Visitor getVisitor(Long id){
        Optional<Visitor> visitor = visitorRepository.findById(id);
        if (visitor.isEmpty()){
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден");
        }else{
            return visitor.get();
        }
    }
}
