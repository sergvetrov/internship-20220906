package internship.vetrov.configuration;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

import static org.springframework.util.ResourceUtils.getFile;


public class ClientCertificateConfig {
    private static final String PASSWORD = "qwerty123";

    @Bean
    public RestTemplate rt() throws Exception {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(getFile("classpath:keystore.jks"), PASSWORD.toCharArray(), PASSWORD.toCharArray())
                .loadTrustMaterial(getFile("classpath:truststore.jks"), PASSWORD.toCharArray(), new TrustSelfSignedStrategy())
                .build();

        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }
}
