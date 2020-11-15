package com.recorda.admin.users.exception;

/**
 * Handles a technical exception
 */
public class TechnicalException extends Exception {

    private static final long serialVersionUID = -3563179314013425040L;

    public TechnicalException(String message) {
        super(message);
    }
}
