package in.wynk.phoenix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionResponse {
    private boolean status;

    private String errorCode;

    private String errorMsg;

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
}
