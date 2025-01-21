package ru.timerdar.CultureBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.AuthRequest;
import ru.timerdar.CultureBooking.dto.AuthorizationResponse;
import ru.timerdar.CultureBooking.exceptions.WrongPasswordException;

@Service
public class AuthenticationProvider {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    public AuthorizationResponse authenticate(AuthRequest authRequest){
        UserDetails adminDetails = adminService.loadUserByUsername(authRequest.getUsername());
        if (!encoder.matches(authRequest.getPassword(), adminDetails.getPassword())){
            throw new WrongPasswordException("Неверный пароль");
        }
        String token = jwtService.generateToken(adminDetails);
        return new AuthorizationResponse(token);
    }
}
