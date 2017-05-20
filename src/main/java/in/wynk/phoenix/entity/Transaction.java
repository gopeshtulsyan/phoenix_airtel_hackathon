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

    private float userUpdatedAmount;

    private float merchantUpdatedAmount;

    private String userConsentId;

    public String getUserConsentId() {
        return userConsentId;
    }

    public void setUserConsentId(String userConsentId) {
        this.userConsentId = userConsentId;
    }

    public float getUserUpdatedAmount() {
        return userUpdatedAmount;
    }

    public void setUserUpdatedAmount(float userUpdatedAmount) {
        this.userUpdatedAmount = userUpdatedAmount;
    }

    public float getMerchantUpdatedAmount() {
        return merchantUpdatedAmount;
    }

    public void setMerchantUpdatedAmount(float merchantUpdatedAmount) {
        this.merchantUpdatedAmount = merchantUpdatedAmount;
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

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", trxId=" + trxId + ", userId=" + userId + ", merchantId=" + merchantId + ", pinCode=" + pinCode + ", createdAt=" + createdAt + ", userUpdatedAmount="
                + userUpdatedAmount + ", merchantUpdatedAmount=" + merchantUpdatedAmount + ", userConsentId=" + userConsentId + "]";
    }

}
