package internship.vetrov;

import com.fasterxml.jackson.databind.ObjectMapper;
import internship.vetrov.configuration.ClientCertificateConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

@SpringBootApplication
public class AppRunner {

//    private static final Logger log = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) throws Exception {
//        log.info("Start main...");
//        SpringApplication.run(AppRunner.class, args);
//        log.info("Finish main...");

        ClientCertificateConfig clientCertificateConfig = new ClientCertificateConfig();
        RestTemplate restTemplate = clientCertificateConfig.rt();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = new URI("https://appapi2.bankid.com/rp/v5.1/auth");

        HashMap<String, String> values = new HashMap<>() {{
            put("endUserIp", "95.47.122.31");
        }};
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        AuthInfo authInfo = restTemplate.postForObject(uri, httpEntity, AuthInfo.class);

        System.out.println("orderRef:    " + authInfo.getOrderRef());
        System.out.println("autoStartToken:   " + authInfo.getAutoStartToken());
        System.out.println("qrStartToken: " + authInfo.getQrStartToken());
        System.out.println("qrStartSecret:   " + authInfo.getQrStartSecret());
    }
}
