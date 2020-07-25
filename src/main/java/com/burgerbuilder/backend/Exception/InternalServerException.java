package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class InternalServerException  extends ApiBaseException{
    public InternalServerException(String message, int errorCode) {
        super(message, errorCode);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public int getErrorCode() {
        return 500;
    }
}
