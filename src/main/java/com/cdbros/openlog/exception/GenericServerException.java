package com.cdbros.openlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericServerException extends RequestException {

    private static final long serialVersionUID = -4515734292614839385L;

    public GenericServerException() {
        this(null, "GENERIC_ERROR");
    }

    public GenericServerException(String message) {
        this(message, "GENERIC_ERROR");
    }

    public GenericServerException(String message, String exceptionCode) {
        super(message, exceptionCode);
    }
}

