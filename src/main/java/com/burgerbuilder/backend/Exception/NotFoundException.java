package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException {

    private int errorCode;

    public NotFoundException(int errorCode,String message) {
        super(message);
        this.errorCode=errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
