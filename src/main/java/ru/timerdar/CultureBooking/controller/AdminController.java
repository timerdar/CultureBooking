package ru.timerdar.CultureBooking.controller;


import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.AdminCreatingDto;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.handler.RestResponsesExceptionsHandler;
import ru.timerdar.CultureBooking.model.Admin;
import ru.timerdar.CultureBooking.service.AdminService;
import ru.timerdar.CultureBooking.service.EmailService;

import java.net.URI;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> registrateNewAdmin(@RequestBody AdminCreatingDto creatingDto){
        Admin createdAdmin = adminService.createNewAdmin(creatingDto);
        log.info("Создан новый админ: " + createdAdmin.toShort());
        return ResponseEntity.created(URI.create("/api/admin/" + createdAdmin.getId())).body(createdAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortAdminDto> getAdminInfo(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getAdminInfo(id));
    }
}
