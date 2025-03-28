package ru.timerdar.CultureBooking.service;


import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.TicketInfoDto;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String senderEmail;

    public void sendTicket(TicketInfoDto ticketInfoDto, byte[] ticketPdf) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(senderEmail);
        helper.setTo(ticketInfoDto.getVisitor().getEmail());
        helper.setSubject(ticketInfoDto.getEvent().getName());
        helper.setText("Уважаемый(-ая) " + ticketInfoDto.getVisitor().getFio() + "!" +
                "Команда Культурной среды благодарит за бронирование места на мероприятие \"" + ticketInfoDto.getEvent().getName() + "\"" +
                "Сектор: " + ticketInfoDto.getSector() +
                "Ряд: " + ticketInfoDto.getSeat().split("-")[0] +
                "Место: " + ticketInfoDto.getSeat().split("-")[1] +
                "\n" +
                "Будем ждать!");

        ByteArrayDataSource bads = new ByteArrayDataSource(ticketPdf, "application/pdf");

        helper.addAttachment("ticket.pdf", bads);

        mailSender.send(message);


    }

}
