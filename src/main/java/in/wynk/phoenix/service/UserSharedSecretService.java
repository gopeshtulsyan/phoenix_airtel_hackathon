package in.wynk.phoenix.service;

import in.wynk.phoenix.utils.EncryptionUtils;

import java.security.SignatureException;

import org.springframework.stereotype.Service;

@Service
public class UserSharedSecretService {

    public static String secret1 = "Aastha";
    public static String secret2 = "Priya";

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

}
