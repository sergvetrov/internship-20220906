package internship.vetrov.controller;

import internship.vetrov.service.QRService;
import internship.vetrov.service.RequestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.time.Instant;

@RestController
public class AuthInfoController {
    private final RequestService requestService;
    private final Instant orderTime = Instant.now();

    public AuthInfoController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping(path = "/code", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQRCodeData(
            @RequestParam String qrStartToken,
            @RequestParam String qrStartSecret,
            @RequestParam String orderRef) throws Exception {

        String status = requestService.getCollectInfo(orderRef).getStatus();
        if (!status.equals("pending")) {
            throw new Exception(); //TODO: make other scenarios
        }
        String qrData = QRService.generateQRdata(qrStartToken, qrStartSecret, orderTime);
        return QRService.generateQRCode(qrData);
    }
}
