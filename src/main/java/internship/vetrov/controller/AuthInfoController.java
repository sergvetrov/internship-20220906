package internship.vetrov.controller;

import internship.vetrov.entity.AuthInfo;
import internship.vetrov.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
public class AuthInfoController {
    @Autowired
    private RequestService requestService;

    @RequestMapping("/auth")
    public AuthInfo getAuthInfo() {
        return requestService.getAuthInfo();
    }

    @RequestMapping("/code")
    void generateQRcodeData(HttpServletResponse response) throws IOException {
        AuthInfo rpResponse = requestService.getAuthInfo();
        String qrStartToken = rpResponse.getQrStartToken();
        String qrStartSecret = rpResponse.getQrStartSecret();

        Instant orderTime = Instant.now();
        String qrTime = Long.toString(orderTime.until(Instant.now(), ChronoUnit.SECONDS));
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(qrStartSecret.getBytes(StandardCharsets.US_ASCII), "HmacSHA256"));
            mac.update(qrTime.getBytes(StandardCharsets.US_ASCII));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        String qrAuthCode = String.format("%064x", new BigInteger(1, mac.doFinal()));
        String qrData = String.join(".", "bankid", qrStartToken, qrTime, qrAuthCode);

        response.sendRedirect("qr-code?url=" + qrData);
    }
}