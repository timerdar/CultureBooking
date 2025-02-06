package ru.timerdar.CultureBooking.exceptions;

import org.springframework.security.core.AuthenticationException;

public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WrongPasswordException(String msg) {
        super(msg);
    }
}
