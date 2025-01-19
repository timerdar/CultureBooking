package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.timerdar.CultureBooking.dto.AuthRequest;
import ru.timerdar.CultureBooking.dto.AuthorizationResponse;
import ru.timerdar.CultureBooking.exceptions.WrongPasswordException;
import ru.timerdar.CultureBooking.service.AdminService;
import ru.timerdar.CultureBooking.service.AuthenticationProvider;
import ru.timerdar.CultureBooking.service.JwtService;

@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    @Autowired
    private AuthenticationProvider provider;
    //TODO обработка WrongPasswordEx
    @PostMapping
    public ResponseEntity<AuthorizationResponse> authenticate(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(provider.authenticate(authRequest));
    }
}
