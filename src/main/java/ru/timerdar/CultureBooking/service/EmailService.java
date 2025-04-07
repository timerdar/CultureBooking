package ru.timerdar.CultureBooking.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.dto.ShortAdminDto;
import ru.timerdar.CultureBooking.dto.TicketInfoDto;

import java.io.*;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}") private String senderEmail;


    @Async
    public void sendTicket(TicketInfoDto ticketInfoDto, byte[] ticketPdf) throws MessagingException, UnsupportedEncodingException {
        String dest = ticketInfoDto.getVisitor().getEmail();
        String subj = "Билет на мероприятие \"" + ticketInfoDto.getEvent().getName() + "\"";

        String text = "<p>Команда Культурной среды благодарит за бронирование места на мероприятие: <span>" + ticketInfoDto.getEvent().getName() + "</span></p>\n" +
                "<p>Сектор: <span>" + ticketInfoDto.getSector() + "</span> Ряд <span>" + ticketInfoDto.getSeat().split("-")[0] +
                "</span> Место <span>" + ticketInfoDto.getSeat().split("-")[1] + "</span></p>" +
                "<p>Будем ждать <b>"+ ticketInfoDto.getEvent().getDate().toString() + "</b></p>" +
                "<p><a href=\"https://kulturnaya-sreda.ru/tickets/" + ticketInfoDto.getUuid() + "\">Отменить билет</a></p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(new InternetAddress(senderEmail, "Культурная среда", "UTF-8"));
        helper.setTo(dest);
        helper.setSubject(subj);
        helper.setText(text, true); // указываем, что текст — это HTML
        helper.addAttachment("ticket.pdf", new ByteArrayResource(ticketPdf));

        mailSender.send(message);
        log.info("Билет отправлен на почту:" + dest);
    }

    @Async
    public void sendBanTicketMessage(TicketInfoDto ticketInfo, ShortAdminDto admin) throws MessagingException {
        String dest = ticketInfo.getVisitor().getEmail();
        String subj = "Администратор отозвал билет на " + ticketInfo.getEvent().getName();
        String text = "Уважаемый(-ая) " + ticketInfo.getVisitor().getFio() + "!"+
                "\nБилет на мероприятие " + ticketInfo.getEvent().getName() + " ("+ ticketInfo.getSeat() +") был отозван администратором." +
                "\nДля подробной информации свяжитесь с " + admin.getName() + " " + admin.getMobilePhone();

        mailSender.send(createMessage(dest, subj, text));
    }

    @Async
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
