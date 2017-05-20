package in.wynk.phoenix.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */


public class User {

    private String id;

    private String msisdn;

    private Map<String, String> sharedSecrets = new HashMap<>();

    private float amount;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Map<String, String> getSharedSecrets() {
        return sharedSecrets;
    }

    public void setSharedSecret(Map<String, String> sharedSecrets) {
        this.sharedSecrets = sharedSecrets;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", msisdn=" + msisdn + ", sharedSecrets=" + sharedSecrets + ", amount=" + amount + "]";
    }

}
