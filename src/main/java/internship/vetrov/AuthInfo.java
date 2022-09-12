package internship.vetrov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthInfo {

    private String orderRef;

    private String autoStartToken;

    private String qrStartToken;
    private String qrStartSecret;
    public String getOrderRef() {
        return orderRef;
    }

    public String getAutoStartToken() {
        return autoStartToken;
    }

    public String getQrStartToken() {
        return qrStartToken;
    }

    public String getQrStartSecret() {
        return qrStartSecret;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }
}
