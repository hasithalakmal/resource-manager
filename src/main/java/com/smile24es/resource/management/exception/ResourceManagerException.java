package com.smile24es.resource.management.exception;

import org.springframework.http.HttpStatus;

public class ResourceManagerException extends RuntimeException {
    private final HttpStatus httpStatusCode;
    private final String errorCode;

    public ResourceManagerException(HttpStatus httpStatusCode, String errorCode, String message, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    public ResourceManagerException(HttpStatus httpStatusCode, String errorCode, String message) {
        this(httpStatusCode, errorCode, message, null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
