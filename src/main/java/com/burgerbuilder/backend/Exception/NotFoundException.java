package com.burgerbuilder.backend.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException {



    public NotFoundException(int errorCode,String message) {
        super(message,errorCode);

    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
