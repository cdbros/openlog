package com.cdbros.openlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProjectException extends RequestException {

    public ProjectException() {
        this(null, "PROJECT_EXCEPTION");
    }

    public ProjectException(String message) {
        this(message, "PROJECT_EXCEPTION");
    }

    public ProjectException(String message, String exceptionCode) {
        super(message, exceptionCode);
    }
}
