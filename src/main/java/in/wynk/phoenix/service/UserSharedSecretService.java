package in.wynk.phoenix.service;

import in.wynk.phoenix.dao.TransactionDao;
import in.wynk.phoenix.dao.UserDao;
import in.wynk.phoenix.dto.PaymentRequest;
import in.wynk.phoenix.dto.TransactionResponse;
import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.entity.User;
import in.wynk.phoenix.utils.EncryptionUtils;
import in.wynk.phoenix.utils.TimeOTP;

import java.security.SignatureException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSharedSecretService {

    public static String          secret1 = "Aastha";
    public static String          secret2 = "Priya";

    @Autowired
    private TransactionDao        otpDao;

    @Autowired
    private UserDao               userDao;

    @Autowired
    private MessageSendingService messageSendingService;

    public String createUserSharedSecret(String msisdn, String deviceId) throws SignatureException {
        if(msisdn == null || deviceId == null) {
            return null;
        }

        String sharedSecret = null;
        sharedSecret = EncryptionUtils.calculateRFC2104HMAC(deviceId, msisdn);
        return Integer.toString(Math.abs(sharedSecret.hashCode()));
    }

    public TransactionResponse makePayment(String userMsisdn, String merchantId, float amount, int pinCode, String userConsentId) {
        TransactionResponse transactionResponse = new TransactionResponse();
        try {
            Transaction transaction = otpDao.deductAmount(userMsisdn, merchantId, amount, pinCode, userConsentId);
            if(transaction.getId() != null)
                transactionResponse.setStatus(true);
        }
        catch (IllegalArgumentException e) {
            transactionResponse.setErrorCode("1001");
            transactionResponse.setErrorCode(e.getMessage());
        }
        catch (IllegalStateException e) {
            transactionResponse.setErrorCode("1002");
            transactionResponse.setErrorMsg(e.getMessage());
        }
        return transactionResponse;
    }

    public User getSharedSecret(String deviceId, String msisdn) throws SignatureException {
        if(StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(msisdn)) {
            throw new IllegalArgumentException("empty deviceId or msisdn");
        }
        String sharedSecret = null;
        User user = userDao.getUserByMsisdn(msisdn);
        if(null == user)
            throw new IllegalStateException("User doesn't exists");
        sharedSecret = user.getSharedSecrets().get(deviceId);
        if(sharedSecret == null) {
            sharedSecret = createUserSharedSecret(msisdn, deviceId);
            user.getSharedSecrets().put(deviceId, sharedSecret);
            userDao.saveUser(user);
        }
        return user;
    }

    public TransactionResponse makePayment(PaymentRequest request) throws SignatureException {
        // using trancation id get transaction log for db - if transaction found
        // send failure response
        TransactionResponse transactionResponse = new TransactionResponse();
        Transaction transaction = otpDao.getTransactionDetailsByUserConsentId(request.getUserConsentId());
        if(null != transaction) {
            transactionResponse.setErrorCode("5000");
            transactionResponse.setErrorMsg("Failure!! Due to unauthorized repeated transactions");
        }
        boolean validRequest = false;
        long currentTime = System.currentTimeMillis() / 1000;
        String time;
        String key;
        User user = getSharedSecret(request.getDeviceId(), request.getMsisdn());
        for(int i = 1; i < 4; i++) {
            time = Long.toHexString(currentTime / (30 * i));
            String calculatedPin;
            calculatedPin = TimeOTP.generateTOTP(user.getSharedSecrets().get(request.getDeviceId()), time, "6", "HmacSHA512");
            int calculatedPinInt = Integer.parseInt(calculatedPin);
            if(calculatedPinInt == request.getPin()) {
                validRequest = true;
                break;
            }
        }
        if(validRequest) {
            // call mock API of Airtel for Money Tranfer
            // If transaction status is success then write in db
            transaction = otpDao.deductAmount(request.getMsisdn(), request.getMerchantId(), Float.parseFloat(request.getPrice()), request.getPin(), request.getUserConsentId());
            transactionResponse.setMerchantId(request.getMerchantId());
            transactionResponse.setStatus(true);
            transactionResponse.setMerchantAmount(transaction.getMerchantUpdatedAmount());
            transactionResponse.setUserMsisdn(request.getMsisdn());
            transactionResponse.setTxnId(transaction.getTrxId());
            messageSendingService.sendMessage(transaction, request.getPrice());

        }
        else {
            transactionResponse.setErrorCode("5001");
            transactionResponse.setErrorMsg("Failure!! Due to invalid pincode");
        }
        return transactionResponse;
    }

}
