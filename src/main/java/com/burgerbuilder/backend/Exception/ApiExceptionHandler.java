package com.burgerbuilder.backend.Exception;


import com.burgerbuilder.backend.DTO.Response.ErrorDTOResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<?> handleApiException (ApiBaseException ex , WebRequest request){
        ErrorDTOResponse errorResponse =new ErrorDTOResponse(ex.getErrorCode(),Map.of("message:",ex.getMessage()),request.getDescription(false));
        return new ResponseEntity<>(errorResponse,ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTOResponse errorDTOResponse =new ErrorDTOResponse();
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        errorDTOResponse.setErrorCode(205);
        errorDTOResponse.setErrors(errors);
        errorDTOResponse.setUri(request.getDescription(false));
        return new ResponseEntity<>(errorDTOResponse,HttpStatus.BAD_REQUEST);
    }

}
