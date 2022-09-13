package internship.vetrov.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.springframework.util.ResourceUtils.getFile;


public class RestTemplateConfigUsingClientCert {
    private static final String PASSWORD = "qwerty123";

    @Bean
    public RestTemplate rt() {
        SSLContext sslContext;
        try {
            sslContext = SSLContextBuilder
                    .create()
                    .loadKeyMaterial(getFile("classpath:keystore.jks"), PASSWORD.toCharArray(), PASSWORD.toCharArray())
                    .loadTrustMaterial(getFile("classpath:truststore.jks"), PASSWORD.toCharArray(), new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | CertificateException |
                 IOException | UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClientBuilder.create().setSSLContext(sslContext).build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }
}
