package in.wynk.phoenix.entity;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */


public class User {

    private String id;

    private String msisdn;

    private String sharedSecret;

    private String deviceId;

    private float amount;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
