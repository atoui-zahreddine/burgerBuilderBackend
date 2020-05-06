package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {
    protected int errorCode;
    public ApiBaseException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    public abstract HttpStatus getHttpStatus();
    public abstract int getErrorCode();
}
