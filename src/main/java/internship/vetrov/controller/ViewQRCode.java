package internship.vetrov.controller;

import internship.vetrov.entity.AuthInfo;
import internship.vetrov.entity.AuthStatus;
import internship.vetrov.entity.CollectInfo;
import internship.vetrov.entity.CompletionData;
import internship.vetrov.service.QRService;
import internship.vetrov.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class ViewQRCode {
    private final RequestService requestService = new RequestService();
    private static final Logger log = Logger.getLogger(ViewQRCode.class.getName());
    private AuthInfo authInfo;
    private AuthStatus authStatus = new AuthStatus();


    @RequestMapping("/qr-code")
    public String viewQR(Model model) {
        authInfo = requestService.getAuthInfo();
        model.addAttribute("autoStartToken", authInfo.getAutoStartToken());
        return "qrCode";
    }

    @RequestMapping("/resultAuth")
    public String viewResult(@RequestParam(value = "personalNumber") String personalNumber,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "givenName") String givenName,
                             @RequestParam(value = "surname") String surname,
                             Model model) {
        model.addAttribute("personalNumber", personalNumber);
        model.addAttribute("name", name);
        model.addAttribute("givenName", givenName);
        model.addAttribute("surname", surname);
        return "authResult";
    }

    @RequestMapping("/getQRData")
    @ResponseBody
    public AuthStatus getActualQRData() {
        CollectInfo collectInfo = requestService.getCollectInfo(authInfo.getOrderRef());
        String qrCode = QRService.generateQRdata(
                authInfo.getQrStartToken(),
                authInfo.getQrStartSecret(),
                QRService.getStartOrder().get(authInfo.getOrderRef()));
        authStatus.setQrCode(qrCode);
        authStatus.setCollectInfo(collectInfo);

        String status = collectInfo.getStatus();
        String hintCode = collectInfo.getHintCode();
        CompletionData completionData = collectInfo.getCompletionData();
        switch (status) {
            case "pending", "fail" -> log.info("hintCode is: " + hintCode);
            case "complete" -> {
                log.info("Completion data:");
                log.info("User:");
                log.info("Personal number: " + completionData.getUser().getPersonalNumber());
                log.info("Name: " + completionData.getUser().getName());
                log.info("Given name: " + completionData.getUser().getGivenName());
                log.info("Surname: " + completionData.getUser().getSurname());
                log.info("Device:");
                log.info("IP address: " + completionData.getDevice().getIpAddress());
                log.info("Cert:");
                log.info("Not before: " + completionData.getCert().getNotBefore());
                log.info("Not after: " + completionData.getCert().getNotAfter());
                log.info("Signature: " + completionData.getSignature());
                log.info("OCSP response: " + completionData.getOcspResponse());
            }
        }
        return authStatus;
    }
}
