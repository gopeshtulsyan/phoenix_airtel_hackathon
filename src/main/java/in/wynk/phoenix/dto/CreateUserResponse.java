package in.wynk.phoenix.dto;

public class CreateUserResponse {

    String msisdn;
    String deviceId;
    String sharedSecret;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    @Override
    public String toString() {
        return "CreateUserResponse [msisdn=" + msisdn + ", deviceId=" + deviceId + ", sharedSecret=" + sharedSecret + "]";
    }

}
