package in.wynk.phoenix.service;

import in.wynk.phoenix.dao.TransactionDao;
import in.wynk.phoenix.dao.UserDao;
import in.wynk.phoenix.dto.TransactionResponse;
import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.entity.User;
import in.wynk.phoenix.utils.EncryptionUtils;

import java.security.SignatureException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSharedSecretService {

    public static String secret1 = "Aastha";
    public static String secret2 = "Priya";

    @Autowired
    TransactionDao otpDao;

    @Autowired
    UserDao userDao;

    public String getUserSharedSecret(String msisdn, String deviceId) {
        // TODO Auto-generated method stub
        return null;
    }

    public String createUserSharedSecret(String msisdn, String deviceId) throws SignatureException {
        if(msisdn == null || deviceId == null) {
            return null;
        }

        String sharedSecret = null;
        sharedSecret = EncryptionUtils.calculateRFC2104HMAC(deviceId, msisdn);
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

    public String getSharedSecret(String deviceId, String msisdn){
        if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(msisdn)){
            throw new IllegalArgumentException("empty deviceId or msisdn");
        }
        String sharedSecret = null;
        User user = userDao.getUserByMsisdn(msisdn);
        if (null == user)
            throw new RuntimeException("User doesn't exists");
        sharedSecret = user.getSharedSecrets().get(deviceId);
        if (sharedSecret == null){
            sharedSecret = getSharedSecret(deviceId, msisdn);
            user.getSharedSecrets().put(deviceId, sharedSecret);
            userDao.saveUser(user);
        }
        return sharedSecret;
    }

}
