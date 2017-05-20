package in.wynk.phoenix.service;

import in.wynk.phoenix.dao.TransactionDao;
import in.wynk.phoenix.dto.TransactionResponse;
import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SignatureException;

@Service
public class UserSharedSecretService {

    public static String secret1 = "Aastha";
    public static String secret2 = "Priya";

    @Autowired
    TransactionDao otpDao;

    public String getUserSharedSecret(String msisdn, String deviceId) {
        // TODO Auto-generated method stub
        return null;
    }

    public String createUserSharedSecret(String msisdn, String deviceId) throws SignatureException {
        if(msisdn == null || deviceId == null) {
            return null;
        }
        String sharedSecretStr = null;
        String sharedSecret = null;
        sharedSecretStr = EncryptionUtils.generateHMACSignature(msisdn, deviceId, secret1);
        sharedSecret = EncryptionUtils.calculateRFC2104HMAC(sharedSecretStr, secret2);

        return sharedSecret;

    }

    public TransactionResponse makePayment(String userMsisdn, String merchantId, float amount, int pinCode, String userConsentId){
        TransactionResponse transactionResponse = new TransactionResponse();
        try{
            Transaction transaction = otpDao.deductAmount(userMsisdn, merchantId, amount, pinCode, userConsentId);
            if (transaction.getId()!=null)
                transactionResponse.setStatus(true);
        }catch (IllegalArgumentException e){
            transactionResponse.setErrorCode("1001");
            transactionResponse.setErrorCode(e.getMessage());
        }catch (IllegalStateException e){
            transactionResponse.setErrorCode("1002");
            transactionResponse.setErrorMsg(e.getMessage());
        }
        return transactionResponse;
    }

}
