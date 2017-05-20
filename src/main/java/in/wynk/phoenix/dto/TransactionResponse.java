package in.wynk.phoenix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionResponse {

    private float merchantAmount;

    private String merchantId;

    private String userMsisdn;

    private boolean status;

    private String errorCode;

    private String errorMsg;

    public float getMerchantAmount() {
        return merchantAmount;
    }

    public void setMerchantAmount(float merchantAmount) {
        this.merchantAmount = merchantAmount;
    }

    public String getUserMsisdn() {
        return userMsisdn;
    }

    public void setUserMsisdn(String userMsisdn) {
        this.userMsisdn = userMsisdn;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "TransactionResponse [merchantAmount=" + merchantAmount + ", merchantId=" + merchantId + ", userMsisdn=" + userMsisdn + ", status=" + status + ", errorCode=" + errorCode
                + ", errorMsg=" + errorMsg + "]";
    }

}
