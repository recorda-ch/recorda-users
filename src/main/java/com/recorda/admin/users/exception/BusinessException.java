package com.recorda.admin.users.exception;

/**
 * Handles a business exception
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -5012438262347130003L;

    public BusinessException(String message) {
        super(message);
    }
}
