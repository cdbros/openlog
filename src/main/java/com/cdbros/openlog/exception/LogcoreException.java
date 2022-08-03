package com.cdbros.openlog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LogcoreException extends RequestException {

    private static final long serialVersionUID = -4201874511790328077L;

    public LogcoreException() {
        this(null, "LOGCORE_EXCEPTION");
    }

    public LogcoreException(String message) {
        this(message, "LOGCORE_EXCEPTION");
    }

    public LogcoreException(String message, String exceptionCode) {
        super(message, exceptionCode);
    }
}
