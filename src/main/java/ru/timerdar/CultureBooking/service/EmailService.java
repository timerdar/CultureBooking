package ru.timerdar.CultureBooking.service;


import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.dto.TicketInfoDto;
import ru.timerdar.CultureBooking.model.Admin;
import ru.timerdar.CultureBooking.model.Ticket;
import ru.timerdar.CultureBooking.model.Visitor;

import java.io.*;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String senderEmail;


    public void sendTicket(TicketInfoDto ticketInfoDto, byte[] ticketPdf) throws MessagingException, UnsupportedEncodingException {
        String dest = ticketInfoDto.getVisitor().getEmail();
        String subj = "Билет на мероприятие \"" + ticketInfoDto.getEvent().getName() + "\"";
        String text = "Уважаемый(-ая) " + ticketInfoDto.getVisitor().getFio() + "!" +
                "\nКоманда Культурной среды благодарит за бронирование места на мероприятие \"" + ticketInfoDto.getEvent().getName() + "\"" +
                "\nСектор: " + ticketInfoDto.getSector() +
                "\nРяд: " + ticketInfoDto.getSeat().split("-")[0] +
                "\nМесто: " + ticketInfoDto.getSeat().split("-")[1] +
                "\n" +
                "Будем ждать!";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(senderEmail);
        helper.setText(text);
        helper.setTo(dest);
        helper.setSubject(subj);
        helper.addAttachment("ticket.pdf", new ByteArrayResource(ticketPdf));

        mailSender.send(message);
    }

    public void sendBanTicketMessage(TicketInfoDto ticketInfo, ShortAdminDto admin) throws MessagingException {
        String dest = ticketInfo.getVisitor().getEmail();
        String subj = "Администратор отозвал билет на " + ticketInfo.getEvent().getName();
        String text = "Уважаемый(-ая) " + ticketInfo.getVisitor().getFio() + "!"+
                "\nБилет на мероприятие " + ticketInfo.getEvent().getName() + " ("+ ticketInfo.getSeat() +") был отозван администратором." +
                "\nДля подробной информации свяжитесь с " + admin.getName() + " " + admin.getMobilePhone();

        mailSender.send(createMessage(dest, subj, text));
    }

    public void sendCancelTicketMessage(TicketInfoDto ticketInfo) throws MessagingException {
        String dest = ticketInfo.getVisitor().getEmail();
        String subj = "Отмена билета на " + ticketInfo.getEvent().getName();
        String text = "Уважаемый(-ая) " + ticketInfo.getVisitor().getFio() + "!"+
                "\nБилет на мероприятие " + ticketInfo.getEvent().getName() + " ("+ ticketInfo.getSeat() +") был отменен." +
                "\nОчень жаль. Ждем вас на следующем мероприятии";

        mailSender.send(createMessage(dest, subj, text));
    }

    public MimeMessage createMessage(String destination, String subject, String text) throws MessagingException {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(new InternetAddress(senderEmail, "Культурная среда", "UTF-8"));
            helper.setTo(destination);
            helper.setSubject(subject);
            helper.setText(text);

            return message;
        }catch (UnsupportedEncodingException e){
            throw new MessagingException("Wrong encoding");
        }


    }

}
