package internship.vetrov.service;

import internship.vetrov.AppRunner;
import internship.vetrov.configuration.RestTemplateConfigUsingClientCert;
import internship.vetrov.entity.AuthInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@Service
public class RequestService {
    private static final Logger log = Logger.getLogger(AppRunner.class.getName());
    private final RestTemplate rt = new RestTemplateConfigUsingClientCert().rt();

    public AuthInfo getAuthInfo() {
        log.info("======Send Request [Start]========");
        String requestBody = "{\"endUserIp\": \"95.47.122.31\"}";//TODO: take IP from user
        String url = "https://appapi2.test.bankid.com/rp/v5.1/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        log.info("Request URL: " + url);
        log.info("Request Method: " + HttpMethod.POST);
        log.info("Request Headers: " + httpEntity.getHeaders());
        log.info("Request Body: " + httpEntity.getBody());

        try {
            return rt.postForObject(new URI(url), httpEntity, AuthInfo.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
