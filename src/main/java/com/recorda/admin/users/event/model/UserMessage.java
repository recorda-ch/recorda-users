package com.recorda.admin.users.event.model;

import com.recorda.admin.users.model.User;

/**
 * Handles an event concerning an operation on a user
 */
public class UserMessage extends Message<User, EventTypeRest> {

    /**
     * Resource is a USER
     */
    public UserMessage() {
        this.setResource(User.class.getSimpleName().toUpperCase());
    }


    @Override
    public String toString() {
        return "User [ id=" + id
                + ", resource= " +resource
                + ", source=" + source
                + ", type=" + type
                + ", payload=" + payload
                + "]";
    }
}
