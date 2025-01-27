package ru.timerdar.CultureBooking.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ExpJwtException extends AuthenticationException {
    public ExpJwtException(String msg) {
        super(msg);
    }
}
