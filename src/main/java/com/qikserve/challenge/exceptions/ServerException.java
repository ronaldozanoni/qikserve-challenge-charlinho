package com.qikserve.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Server exception.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Server exception.
     *
     * @param message the message
     */
    public ServerException(String message) {
        super(message);
    }

}
