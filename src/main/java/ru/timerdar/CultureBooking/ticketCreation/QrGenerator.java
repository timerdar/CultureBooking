package ru.timerdar.CultureBooking.ticketCreation;

import io.nayuki.qrcodegen.QrCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class QrGenerator {

    private String url;

    public byte[] generateTicketQrImage(UUID ticketUuid) throws IOException {
        QrCode qr = QrCode.encodeText(this.url + ticketUuid.toString(), QrCode.Ecc.HIGH);
        BufferedImage image = toImage(qr, 4, 5, 0xFFFFFF, 0x000000);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage(
                (qr.size + border * 2) * scale,
                (qr.size + border * 2) * scale,
                BufferedImage.TYPE_INT_RGB
        );
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }
}
