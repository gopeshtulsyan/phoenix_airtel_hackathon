package in.wynk.phoenix.entity;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */

public class Transaction {

    private String id;

    private String trxId;

    private String userId;

    private String merchantId;

    private int  pinCode;

    private long createdAt;

    private float amount;

    private String userConsentId;

    public String getUserConsentId() {
        return userConsentId;
    }

    public void setUserConsentId(String userConsentId) {
        this.userConsentId = userConsentId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
