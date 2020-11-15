package com.recorda.admin.users.exception;

/**
 * A business exception concerning feature accessibility for a user
 */
public class FeatureException extends BusinessException {

    private static final long serialVersionUID = 2762498921120828694L;

    public FeatureException(String message) {
        super(message);
    }
}
