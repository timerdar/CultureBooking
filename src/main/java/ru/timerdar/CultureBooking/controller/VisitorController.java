package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.model.Visitor;
import ru.timerdar.CultureBooking.service.VisitorService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitor(@PathVariable("id") Long id){
        return ResponseEntity.ok(visitorService.getVisitor(id));
    }

}
