package in.wynk.phoenix.dao;

import in.wynk.phoenix.entity.Transaction;
import in.wynk.phoenix.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */
@Component
public class TransactionDao {

    @Autowired
    MongoTemplate hackMongoTemplate;

    public final static String TRANSACTION_COLLECTION_NAME = "transaction";

    public final static String USER_COLLECTION_NAME = "users";

    public final static String MERCHANT_COLLECTION_NAME = "merchants";

    public Transaction deductAmount(String userMsisdn, String merchantId, float amount, int pinCode, String userConsentId){
        Criteria userCriteria = Criteria.where("msisdn").is(userMsisdn);
        Query query = new Query();
        query.addCriteria(userCriteria);
        User user = hackMongoTemplate.findOne(query, User.class, USER_COLLECTION_NAME);
        if (user == null)
            throw new IllegalArgumentException("User not found");

        if (user.getAmount() < amount)
            throw new IllegalStateException("Not enough money in user account");

        Criteria merchantCriteria = Criteria.where("msisdn").is(merchantId);
        query = new Query();
        query.addCriteria(merchantCriteria);

        User merchant = hackMongoTemplate.findOne(query, User.class, USER_COLLECTION_NAME);

        if (null == merchant)
            throw new IllegalArgumentException("Merchant not found");

        user.setAmount(user.getAmount()-amount);

        hackMongoTemplate.save(user, USER_COLLECTION_NAME);

        merchant.setAmount(merchant.getAmount() + amount);

        hackMongoTemplate.save(merchant, USER_COLLECTION_NAME);

        Transaction transaction = new Transaction();
        transaction.setMerchantId(merchantId);
        transaction.setTrxId(UUID.randomUUID().toString());
        transaction.setCreatedAt(System.currentTimeMillis());
        transaction.setUserId(userMsisdn);
        transaction.setMerchantUpdatedAmount(merchant.getAmount());
        transaction.setUserUpdatedAmount(user.getAmount());
        transaction.setPinCode(pinCode);
        transaction.setUserConsentId(userConsentId);

        hackMongoTemplate.save(transaction, TRANSACTION_COLLECTION_NAME);

        return transaction;
    }

    public Transaction getTransactionDetailsByUserConsentId(String userConsentId){
        if (StringUtils.isEmpty(userConsentId)){
            throw new IllegalArgumentException("Empty userConsentId");
        }
        Criteria criteria = Criteria.where("userConsentId").is(userConsentId);
        Query query = new Query();
        query.addCriteria(criteria);
        return hackMongoTemplate.findOne(query, Transaction.class, TRANSACTION_COLLECTION_NAME);
    }
}
