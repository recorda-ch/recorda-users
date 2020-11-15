package com.recorda.admin.users.service;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.FeatureException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.filter.LocationFilter;
import com.recorda.admin.users.i18n.MessageResolver;
import com.recorda.admin.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Provides a {@link UserService} implementation based on MongoDB datastore
 */
@Service
public class MongoUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(MongoUserService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MessageResolver messageResolver;

    @Autowired
    private LocationFilter locationFilter;

    @Override
    public User add(User user) throws BusinessException, TechnicalException {

        /*
         * RULE 1 : only user requesting from Switzerland are authorized to add users
         */
        if (!locationFilter.filter(user)) {
            throw new FeatureException(messageResolver.getContent("business.error.user.ip.not.eligible", null));
        }

        /*
         * RULE 2 : an email is related to a single user
         *
         * Check whether there is already a user with provided email
         */
        List<User> matchingUsers = findByEmail(user.getEmail());
        if ( matchingUsers != null && matchingUsers.size() > 0) {
            logger.debug(String.format("Found a user with email: %s", user.getEmail()));
            throw new UserException(messageResolver.getContent("business.error.user.dupplicate.email", new Object[] { user.getEmail() }));
        }

        return mongoTemplate.save(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User partialUpdate(String id, HashMap<String, String> fields) {
        Query query = new Query(new Criteria("id").is(id));
        Update update = new Update();

        // Set each field
        fields.entrySet().stream()
                .forEach(f -> {
                    update.set(f.getKey(),f.getValue());
                });

        // Overall update
        mongoTemplate.updateFirst(query, update, User.class);

        return findById(id);
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    @Override
    public List<User> findByEmail(String email) {

        // Forge query for filtering
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        // Querying
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }


    @Override
    public List<User> findByName(String name) {

        // Forge query for filtering
        Query query = new Query();
        query.addCriteria(Criteria.where("lastname").is(name));

        // Querying
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
