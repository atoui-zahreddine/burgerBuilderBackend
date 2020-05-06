package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends ApiBaseException{
    public AuthorizationException(String message, int errorCode) {
        super(message, errorCode);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
