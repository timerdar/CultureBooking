package ru.timerdar.CultureBooking.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.entities.Visitor;
import ru.timerdar.CultureBooking.repositories.VisitorRepository;
import ru.timerdar.CultureBooking.responses.MessageResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @PostMapping
    public ResponseEntity<?> addVisitor(@RequestBody Visitor visitor){
        Optional<Visitor> alreadyCreated = visitorRepository.findByNameAndSurnameAndFathernameAndCategory(
                visitor.getName(), visitor.getSurname(), visitor.getFathername(), visitor.getCategory()
        );
        if (alreadyCreated.isEmpty()){
            try{
                Visitor v = visitorRepository.save(visitor);
                return ResponseEntity.created(URI.create("/api/visitors/" + v.getId())).body(v);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            }
        }else{
            return ResponseEntity.ok(alreadyCreated);
        }
    }

    @GetMapping
    public ResponseEntity<List<Visitor>> getVisitorsList(){
        return ResponseEntity.ok(visitorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVisitorById(@PathVariable Long id){
        Optional<Visitor> visitor = visitorRepository.findById(id);
        if (visitor.isPresent()){
            return ResponseEntity.ok(visitor.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
