package ru.timerdar.CultureBooking.exceptions;

public class TicketStatusChangingException extends Exception{
    public TicketStatusChangingException(String message){
        super(message);
    }
}
