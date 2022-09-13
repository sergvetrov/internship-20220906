package internship.vetrov.controller;

import internship.vetrov.entity.AuthInfo;
import internship.vetrov.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthInfoController {
    @Autowired
    private RequestService requestService;

    @RequestMapping
    public AuthInfo getAuthInfo() {
        return requestService.getAuthInfo();
    }
}