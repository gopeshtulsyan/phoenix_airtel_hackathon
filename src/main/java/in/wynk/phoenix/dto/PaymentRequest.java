package in.wynk.phoenix.dto;

import in.wynk.phoenix.utils.CommonUtils;

public class PaymentRequest {

    private String userConsentId;
    private String msisdn;
    private String deviceId;
    private int    pin;
    private String merchantId;
    private String price;

    private long latitude;

    private long longitude;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = CommonUtils.get10DigitMsisdn(msisdn);
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = CommonUtils.get10DigitMsisdn(merchantId);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserConsentId() {
        return userConsentId;
    }

    public void setUserConsentId(String tid) {
        this.userConsentId = userConsentId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PaymentRequest [userConsentId=" + userConsentId + ", msisdn=" + msisdn + ", deviceId=" + deviceId + ", pin=" + pin + ", merchantId=" + merchantId + ", price=" + price + "]";
    }

}
