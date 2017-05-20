package in.wynk.phoenix.dao;

import in.wynk.phoenix.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by a1dmiuxe(gopesh.tulsyan) on 20/05/17.
 */

public class UserDao {
    @Autowired
    MongoTemplate hackMongoTemplate;

    public final static String TRANSACTION_COLLECTION_NAME = "transaction";

    public final static String USER_COLLECTION_NAME = "users";

    public final static String MERCHANT_COLLECTION_NAME = "merchants";

    public User getUserByMsisdn(String msisdn){
        Criteria criteria = Criteria.where("msisdn").is(msisdn);
        Query query = new Query();
        query.addCriteria(criteria);
        return hackMongoTemplate.findOne(query, User.class, USER_COLLECTION_NAME);
    }

    public void saveUser(User user){
        if (null == user)
            throw new IllegalArgumentException("Null user");
        if (user.getMsisdn() == null)
            throw new IllegalArgumentException("User without msisdn");
        hackMongoTemplate.save(user);
    }
}
