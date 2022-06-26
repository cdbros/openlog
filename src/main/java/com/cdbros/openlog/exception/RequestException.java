package com.cdbros.openlog.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public abstract class RequestException extends RuntimeException {

    private final String exceptionCode;

    protected final transient Map<String, Object> attributes;

    RequestException() {
        this(null);
    }

    RequestException(String message) {
        this(message, "GENERIC_EXCEPTION");
    }

    RequestException(String message, String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
        attributes = Collections.emptyMap();
    }
}
