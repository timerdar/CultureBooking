package ru.timerdar.CultureBooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.timerdar.CultureBooking.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;


}
