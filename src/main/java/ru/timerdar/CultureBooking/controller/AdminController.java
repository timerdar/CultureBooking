package ru.timerdar.CultureBooking.controller;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.AdminCreatingDto;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.model.Admin;
import ru.timerdar.CultureBooking.service.AdminService;

import java.net.URI;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> registrateNewAdmin(AdminCreatingDto creatingDto){
        Admin createdAdmin = adminService.createNewAdmin(creatingDto);
        return ResponseEntity.created(URI.create("/api/admin/" + createdAdmin.getId())).body(createdAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortAdminDto> getAdminInfo(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getAdminInfo(id));
    }
}
