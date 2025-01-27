package ru.timerdar.CultureBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.AuthRequest;
import ru.timerdar.CultureBooking.dto.AuthorizationResponse;
import ru.timerdar.CultureBooking.service.AuthenticationProvider;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    @Autowired
    private AuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<AuthorizationResponse> authenticate(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(provider.authenticate(authRequest));
    }
}
