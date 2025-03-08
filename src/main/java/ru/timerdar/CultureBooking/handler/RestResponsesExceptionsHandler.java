package ru.timerdar.CultureBooking.handler;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.timerdar.CultureBooking.dto.ErrorMessage;
import ru.timerdar.CultureBooking.exceptions.ExpJwtException;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.exceptions.TicketStatusChangingException;
import ru.timerdar.CultureBooking.exceptions.WrongPasswordException;

import java.io.IOException;

@ControllerAdvice
public class RestResponsesExceptionsHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponsesExceptionsHandler.class);

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundExceptionHandle(Exception ex, WebRequest webRequest){
        log.error("Не найдено: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage("Not found", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> illegalArgsExceptionHandle(Exception ex, WebRequest webRequest){
        log.error("Неверные данные: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage("Bad Request", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TicketReservationException.class, TicketStatusChangingException.class})
    public ResponseEntity<ErrorMessage> ticketReservationExceptionHandle(Exception ex, WebRequest webRequest){
        log.error("Ошибка работы с билетом: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage("Ticket changing error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WrongPasswordException.class, JwtException.class, AccessDeniedException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorMessage> authenticationExceptionHandle(Exception ex, WebRequest webRequest){
        log.error("Ошибка пароля: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage("Authentication error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<ErrorMessage> IOExceptionHandler(Exception ex, WebRequest webRequest){
        log.error("Ошибка работы с файлом: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage("File uploading error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> otherErrorHandle(Exception ex, WebRequest webRequest){
        log.error("Глобальная ошибка: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorMessage("Unexpected error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }


}
