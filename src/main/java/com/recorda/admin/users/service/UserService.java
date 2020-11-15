package com.recorda.admin.users.service;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.TechnicalException;
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
    User partialUpdate(String id, HashMap<String, String> fields) throws BusinessException;

    /**
     * Creates a new user
     *
     * @param user new user to be created
     * @return the new whole user
     */
    User add(User user) throws BusinessException, TechnicalException;


    /**
     * Entire update on a user resource
     * @param user to update
     * @return updated user
     */
    User update(User user) throws BusinessException;

    /**
     * Find all users
     *
     * @return a list of users
     */
    List<User> findAll() throws BusinessException;

    /**
     * Finds a user given its id
     * @param id user id
     * @return a matching user if any
     */
    User findById(String id) throws BusinessException;

    /**
     * Finds a user given its email
     *
     * @param email user email
     * @return list of matching users
     */
    List<User> findByEmail(String email) throws BusinessException;

    /**
     * Finds a user given its name
     *
     * @param name user name
     * @return list of matching users
     */
    List<User> findByName(String name) throws BusinessException;
}
