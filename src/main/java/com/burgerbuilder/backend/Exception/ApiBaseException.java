package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {

    public ApiBaseException(String message) {
        super(message);
    }
    public abstract HttpStatus getHttpStatus();
    public abstract int getErrorCode();
}
