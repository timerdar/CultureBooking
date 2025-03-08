package ru.timerdar.CultureBooking.service;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PdfGenerationService {

    public static byte[] generateTicketPdf(Ticket ticket, String uri, Visitor visitor, Event event, Sector sector, Seat seat, String path, Poster poster) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        PdfFont font = PdfFontFactory.createFont("fonts/Montserrat-VariableFont_wght.ttf", PdfEncodings.IDENTITY_H);

        Document document = new Document(pdf);

        Rectangle pageSize = pdf.getDefaultPageSize();

        ImageData imageData = ImageDataFactory.create(poster.getImageData());
        Image background = new Image(imageData);

        //background.scaleAbsolute(pageSize.getWidth(), pageSize.getHeight());
        //background.setFixedPosition(0, 0);

        //document.add(background);

        document.setTextAlignment(TextAlignment.CENTER);

        document.setFont(font).setBold();
        document.add(new Image(ImageDataFactory.create(path + "image/ks.png")).scaleAbsolute(pageSize.getWidth()/10, pageSize.getWidth()/10).setHorizontalAlignment(HorizontalAlignment.CENTER));
        document.add(background.scaleAbsolute(pageSize.getWidth()/2, pageSize.getWidth()/2).setHorizontalAlignment(HorizontalAlignment.CENTER));
        document.add(new Paragraph("Билет").setBold().setFontSize(24));

        Link link = new Link("Проверить билет онлайн (ссылка)", PdfAction.createURI(uri + ticket.getUuid()));
        document.add(new Paragraph().setBold().add(link).setFontSize(30));
        BarcodeQRCode qrCode = new BarcodeQRCode(uri + ticket.getUuid());
        document.add(new Paragraph("Посетитель: " + visitor.toString()).setFontSize(30));
        document.add(new Paragraph("Мероприятие: " + event.getName()).setFontSize(25));
        document.add(new Paragraph("Сектор: " + sector.getName() + " Ряд: " + seat.getRowAndSeatNumber().split("-")[0] + " Место: " + seat.getRowAndSeatNumber().split("-")[1]).setBold().setFontSize(30));
        document.add(new Paragraph( "Дата проведения: " + event.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).setFontSize(25));
        document.add(new Image(qrCode.createFormXObject(pdf)).scaleAbsolute(pageSize.getWidth()/10, pageSize.getWidth()/10).setHorizontalAlignment(HorizontalAlignment.CENTER));

        document.add(new Paragraph("Вы можете отменить билет (освободить бронь) по ссылке выше. При входе билеты будут использованы"));

        document.close();
        return outputStream.toByteArray();
    }

}
