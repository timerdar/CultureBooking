package ru.timerdar.CultureBooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timerdar.CultureBooking.dto.AuthRequest;
import ru.timerdar.CultureBooking.dto.AuthorizationResponse;
import ru.timerdar.CultureBooking.handler.RestResponsesExceptionsHandler;
import ru.timerdar.CultureBooking.service.AuthenticationProvider;


@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<AuthorizationResponse> authenticate(@RequestBody AuthRequest authRequest){
        AuthorizationResponse authorizationResponse = provider.authenticate(authRequest);
        log.info("Авторизация: " + authRequest.getUsername());
        return ResponseEntity.ok(authorizationResponse);
    }
}
