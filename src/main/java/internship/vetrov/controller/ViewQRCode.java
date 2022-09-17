package internship.vetrov.controller;

import internship.vetrov.entity.AuthInfo;
import internship.vetrov.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewQRCode {
    private final RequestService requestService;

    public ViewQRCode(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping("/qr-code")
    public String viewQR(Model model) {
        AuthInfo rpResponse = requestService.getAuthInfo();
        String qrStartToken = rpResponse.getQrStartToken();
        String qrStartSecret = rpResponse.getQrStartSecret();
        String orderRef = rpResponse.getOrderRef();

        model.addAttribute("qrStartToken", qrStartToken);
        model.addAttribute("qrStartSecret", qrStartSecret);
        model.addAttribute("orderRef", orderRef);
        return "qrCode";
    }
}
