package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApiBaseException {
    private int errorCode;

    public BadCredentialsException(int errorCode,String message) {
        super(message);
        this.errorCode=errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
