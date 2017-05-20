package in.wynk.phoenix.entity;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */

public class Merchant {
    private String id;

    private String msisdn;

    private float amount;

    private String merchantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
