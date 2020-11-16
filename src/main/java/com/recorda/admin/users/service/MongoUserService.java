package com.recorda.admin.users.service;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.FeatureException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.filter.LocationFilter;
import com.recorda.admin.users.helper.IpValidator;
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

    @Autowired
    private IpValidator ipValidator;

    /**
     * Adds a user regarding some rules :
     *
     * <ul>
     *   <li>RULE 0 : a valid user IP MUST be provided</li>
     *   <li>RULE 1 : only user requesting from Switzerland are authorized to add users</li>
     *   <li>RULE 2 : an email is related to a single user</li>
     * </ul>
     *
     * @param user new user to be created
     * @return
     * @throws BusinessException
     * @throws TechnicalException
     */
    @Override
    public User add(User user) throws BusinessException, TechnicalException {

        /*
         * Apply [RULE 0]
         */
        if (!ipValidator.validate(user.getIp())) {
            String errorMessage = messageResolver.getContent("business.error.user.ip.invalid", null);
            throw new UserException(errorMessage);
        }

        /*
         * Apply [RULE 1]
         */
        if (!locationFilter.filter(user)) {
            throw new FeatureException(messageResolver.getContent("business.error.user.ip.not.eligible", null));
        }

        /*
         * Apply [RULE 2]
         *
         * Check whether there is already a user with provided email
         */
        List<User> matchingUsers = findByEmail(user.getEmail());
        if ( matchingUsers != null && matchingUsers.size() > 0) {
            logger.debug(String.format("Found a user with email: %s", user.getEmail()));
            throw new UserException(messageResolver.getContent("business.error.user.dupplicate.email", new Object[] { user.getEmail() }));
        }

        logger.debug(String.format("Adding user: %s", user.toString()));
        return mongoTemplate.save(user);
    }

    @Override
    public User partialUpdate(String id, HashMap<String, String> fields) throws UserException {

        if (findById(id) == null) {
            String errorMessage = messageResolver.getContent("business.error.user.id.invalid", new Object[] { id });
            throw new UserException(errorMessage);
        }

        Query query = new Query(new Criteria("id").is(id));
        Update update = new Update();

        // Set each field
        fields.entrySet().stream()
                .forEach(f -> {
                    update.set(f.getKey(),f.getValue());
                });

        // Overall update
        logger.debug(String.format("Adding user with id: %s", id));
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
