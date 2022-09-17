package internship.vetrov.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectInfo {

    private String orderRef;
    private String status;
    private String hintCode;

    public String getOrderRef() {
        return orderRef;
    }

    public String getStatus() {
        return status;
    }

    public String getHintCode() {
        return hintCode;
    }
}
