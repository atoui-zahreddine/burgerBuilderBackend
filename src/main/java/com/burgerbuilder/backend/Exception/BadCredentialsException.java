package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApiBaseException {

    public BadCredentialsException(int errorCode,String message) {
        super(message,errorCode);
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
