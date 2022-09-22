package internship.vetrov.controller;

import internship.vetrov.entity.AuthInfo;
import internship.vetrov.service.QRService;
import internship.vetrov.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewQRCode {
    private final RequestService requestService = new RequestService();
    private AuthInfo authInfo;


    @RequestMapping("/qr-code")
    public String viewQR(Model model) throws Exception {
        authInfo = requestService.getAuthInfo();
        model.addAttribute("qrData", getActualQRData()); //TODO: mb dont need
        return "qrCode";
    }

    @RequestMapping("/getQRData")
    @ResponseBody
    public String getActualQRData() throws Exception {
        String status = requestService.getCollectInfo(authInfo.getOrderRef()).getStatus();
        if (!status.equals("pending")) {
            throw new Exception(); //TODO: make other scenarios
        }

        return QRService.generateQRdata(
                authInfo.getQrStartToken(),
                authInfo.getQrStartSecret(),
                QRService.getStartOrder().get(authInfo.getOrderRef()));
    }
}
