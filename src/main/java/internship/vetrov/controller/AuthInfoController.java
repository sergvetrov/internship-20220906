package internship.vetrov.controller;

import internship.vetrov.service.QRService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
public class AuthInfoController {

    @RequestMapping(path = "/code", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQRCodeData(@RequestParam String qrData) throws Exception {
        return QRService.generateQRCode(qrData);
    }
}
