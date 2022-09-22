package internship.vetrov.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

public class QRService {
    private static final ConcurrentHashMap<String, Instant> startOrder = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Instant> getStartOrder() {
        return startOrder;
    }

    public static void addOrder(String orderRef, Instant startOrderTime) {
        startOrder.put(orderRef, startOrderTime);
    }

    public static String generateQRdata(String qrStartToken, String qrStartSecret, Instant startOrderTime) {
        String qrTime = Long.toString(startOrderTime.until(Instant.now(), ChronoUnit.SECONDS));
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(qrStartSecret.getBytes(StandardCharsets.US_ASCII), "HmacSHA256"));
            mac.update(qrTime.getBytes(StandardCharsets.US_ASCII));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        String qrAuthCode = String.format("%064x", new BigInteger(1, mac.doFinal()));
        return String.join(".", "bankid", qrStartToken, qrTime, qrAuthCode);
    }

    public static BufferedImage generateQRCode(String qrData) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 250, 250);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
