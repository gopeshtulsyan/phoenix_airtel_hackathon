package in.wynk.phoenix.dto;

public class CreateUserRequest {

    String msisdn;
    String deviceId;

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

    @Override
    public String toString() {
        return "CreateUserRequest [msisdn=" + msisdn + ", deviceId=" + deviceId + "]";
    }

}
