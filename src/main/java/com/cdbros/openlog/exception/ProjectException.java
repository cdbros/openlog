package com.cdbros.openlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProjectException extends RequestException {

    private static final long serialVersionUID = -2447196143132490869L;

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
