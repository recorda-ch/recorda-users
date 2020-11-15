package com.recorda.admin.users.exception;

/**
 * A business exception concerning users management
 */
public class UserException extends BusinessException {

    private static final long serialVersionUID = 1272856991806538880L;

    public UserException(String message) {
        super(message);
    }
}
