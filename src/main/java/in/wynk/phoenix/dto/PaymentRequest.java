package in.wynk.phoenix.dto;

public class PaymentRequest {
	String tid;
	String msisdn;
	String deviceId;
	int pin;
	String merchantId;
	String price;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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
		this.merchantId = merchantId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "PaymentRequest [tid=" + tid + ", msisdn=" + msisdn
				+ ", deviceId=" + deviceId + ", pin=" + pin + ", merchantId="
				+ merchantId + ", price=" + price + "]";
	}

}