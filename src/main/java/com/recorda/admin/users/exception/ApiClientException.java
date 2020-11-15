package com.recorda.admin.users.exception;

/**
 * A technical exception denoting a problem when requesting a tiers API
 */
public class ApiClientException extends TechnicalException {

    private static final long serialVersionUID = -1335550621213425457L;

    public ApiClientException(String message) {
        super(message);
    }
}
