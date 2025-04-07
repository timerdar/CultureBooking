package ru.timerdar.CultureBooking.service;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.forms.form.element.TextArea;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.timerdar.CultureBooking.model.*;
import ru.timerdar.CultureBooking.model.Event;
import com.itextpdf.layout.borders.Border;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PdfGenerationService {

    public static byte[] generateTicketPdf(Ticket ticket, Visitor visitor, Event event, Sector sector, Seat seat, String path, Poster poster) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        PdfFont font = PdfFontFactory.createFont("fonts/Montserrat-SemiBold.ttf", PdfEncodings.IDENTITY_H);

        Document document = new Document(pdf);
        document.setFont(font);

        PageSize pageSize = document.getPdfDocument().getDefaultPageSize();
        document.add(new Image(ImageDataFactory.create(path + "image/ks.png")).scaleAbsolute(pageSize.getWidth()/10, pageSize.getWidth()/10).setHorizontalAlignment(HorizontalAlignment.CENTER));
        document.add(new Paragraph("БИЛЕТ НА МЕРОПРИЯТИЕ")
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        ImageData imageData = ImageDataFactory.create(poster.getImageData());

        Image image = new Image(imageData);

        float maxWidth = document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin();

        image.scaleToFit(maxWidth, 280); // ограничиваем по высоте, если нужно
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        image.setMarginBottom(15);

        document.add(image);

        document.add(new Paragraph("Посетитель:")
                .setBold());
        document.add(new Paragraph(visitor.getSurname() + " " +
                visitor.getName() + " " +
                visitor.getFathername())
                .setFontSize(18)
                .setMarginBottom(5));

        document.add(new Paragraph("Мероприятие:")
                .setBold());
        document.add(new Paragraph(event.getName())
                .setFontSize(18)
                .setMarginBottom(5));

        document.add(new Paragraph("Дата проведения:")
                .setBold());
        document.add(new Paragraph(event.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .setFontSize(18)
                .setMarginBottom(5));

        String[] seatParts = seat.getRowAndSeatNumber().split("-");
        document.add(new Paragraph("Сектор / Ряд / Место:")
                .setBold());
        document.add(new Paragraph(sector.getName() + " / " + seatParts[0] + " / " + seatParts[1])
                .setFontSize(18)
                .setMarginBottom(5));  // Сектор, ряд, место

        // Благодарность
        document.add(new Paragraph("Спасибо за бронирование!\nЖдём вас на мероприятии.")
                .setItalic()
                .setFontSize(5)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(5));

        document.close();
        return outputStream.toByteArray();
    }

}
