package com.recorda.admin.users.service;

import com.mongodb.client.result.UpdateResult;
import com.recorda.admin.users.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * Handles users management actions
 */
public interface UserService {

    /**
     * Partial update on multiple fields
     *
     * @param id user id
     * @param fields map of fields (name and value)
     * @return updated user
     */
    User updateMultiple(String id, HashMap<String, String> fields);

    /**
     * Creates a new user
     *
     * @param user new user to be created
     * @return the new whole user
     */
    User add(User user);


    /**
     * Entire update on a user resource
     * @param user to update
     * @return updated user
     */
    User update(User user);

    /**
     * Find all users
     *
     * @return a list of users
     */
    List<User> findAll();

    /**
     * Finds a user given its id
     * @param id user id
     * @return a matching user if any
     */
    User findById(String id);

    /**
     * Finds a user given its email
     *
     * @param email user email
     * @return list of matching users
     */
    List<User> findByEmail(String email);

    /**
     * Finds a user given its name
     *
     * @param name user name
     * @return list of matching users
     */
    List<User> findByName(String name);
}
