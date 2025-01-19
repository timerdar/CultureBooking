package ru.timerdar.CultureBooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.timerdar.CultureBooking.dto.AuthRequest;
import ru.timerdar.CultureBooking.dto.AuthorizationResponse;

@Controller
@RequestMapping("/api/authenticate")
public class AuthController {


    //TODO доделать получение токена по паролю и логину (проверка хэша пароля с тем, что получаем по loadByUsername)
    @PostMapping
    public ResponseEntity<AuthorizationResponse> authenticate(@RequestBody AuthRequest authRequest){
        return null;
    }
}
