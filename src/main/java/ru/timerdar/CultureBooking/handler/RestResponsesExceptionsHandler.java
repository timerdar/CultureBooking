package ru.timerdar.CultureBooking.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.timerdar.CultureBooking.dto.ErrorMessage;
import ru.timerdar.CultureBooking.exceptions.TicketReservationException;
import ru.timerdar.CultureBooking.exceptions.TicketStatusChangingException;
import ru.timerdar.CultureBooking.exceptions.WrongPasswordException;

@ControllerAdvice
public class RestResponsesExceptionsHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundExceptionHandle(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage("Not found", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> illegalArgsExceptionHandle(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage("Bad Request", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TicketReservationException.class, TicketStatusChangingException.class})
    public ResponseEntity<ErrorMessage> ticketReservationExceptionHandle(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage("Ticket changing error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<ErrorMessage> authenticationExceptionHandle(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage("Authentication error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> otherErrorHandle(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorMessage("Unexpected error", ex.getMessage(), webRequest.getDescription(false)), HttpStatus.BAD_REQUEST);
    }
}
