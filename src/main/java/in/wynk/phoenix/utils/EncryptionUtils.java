package in.wynk.phoenix.utils;

import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class EncryptionUtils {

    public static String generateHMACSignature(String msisdn, String deviceId, String secret) {
        String signature = StringUtils.EMPTY;
        String digestString = new StringBuilder(msisdn).append(deviceId).toString();
        try {
            signature = calculateRFC2104HMAC(digestString, secret);
        }
        catch (SignatureException e) {

        }
        return signature;
    }

    public static String calculateRFC2104HMAC(String data, String secretKey) throws java.security.SignatureException {
        String result;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(key);
            byte[] authentication = mac.doFinal(data.getBytes());
            result = new String(Base64.encodeBase64(authentication));

        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

}
