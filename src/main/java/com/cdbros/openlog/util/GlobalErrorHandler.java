package com.cdbros.openlog.util;

import com.cdbros.openlog.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> otherExceptions(Exception ex, WebRequest request) {
        ResponseStatus annotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (null != annotation) {
            return fromException(ex, request, annotation.code());
        }
        return unhandledExceptions(ex, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> mediaTypeNotSupported(Exception ex, WebRequest request) {
        return fromException(ex, request, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, BindException.class})
    public ResponseEntity<Map<String, Object>> badRequestException(Exception ex, WebRequest request) {
        return fromException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Map<String, Object>> clientErrorException(HttpStatusCodeException ex, WebRequest request) {
        return fromException(ex, request, ex.getStatusCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> methodNotSupported(Exception ex, WebRequest request) {
        return fromException(ex, request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<Map<String, Object>> fromException(Exception ex, WebRequest request, HttpStatus httpStatus) {
        Map<String, Object> result = fromException(httpStatus, ex, request);
        return new ResponseEntity<>(result, httpStatus);
    }

    private static Map<String, Object> fromException(HttpStatus httpStatus, Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> result = new HashMap<>();
        result.put("timeStamp", System.currentTimeMillis());
        result.put("status", httpStatus.value());
        result.put("error", httpStatus.getReasonPhrase());
        result.put("exception", ex.getClass().getCanonicalName());
        result.put("message", ex.getMessage());
        result.put("path", getPath(request));

        if (ex instanceof RequestException) {
            var rex = (RequestException) ex;
            result.put("exceptionCode", rex.getExceptionCode());

            if (rex.getAttributes() != null) {
                Map<String, Object> attributes = rex.getAttributes();
                attributes.keySet().forEach(key -> result.put(key, attributes.get(key)));
            }
        }
        return result;
    }

    private static Map<String, Object> maskingException(WebRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("timeStamp", System.currentTimeMillis());
        result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        result.put("exception", "Undisclosed");
        result.put("message", "We cannot handle your request now. Please try again later");
        result.put("path", getPath(request));
        return result;
    }

    private static String getPath(WebRequest request) {
        String path = request.getDescription(false);
        if (!path.isBlank()) {
            path = path.replaceFirst("uri=", "");
        }
        return path;
    }

    private ResponseEntity<Map<String, Object>> unhandledExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> result = maskingException(request);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

