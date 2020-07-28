package com.burgerbuilder.backend.Exception;


import com.burgerbuilder.backend.DTO.Response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<?> handleApiException (ApiBaseException ex , WebRequest request){
        ErrorResponse errorResponse =new ErrorResponse(ex.getErrorCode(),Map.of("message:",ex.getMessage()),request.getDescription(false));
        return new ResponseEntity<>(errorResponse,ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse =new ErrorResponse();
        Map<String,String> errors=ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        errorResponse.setErrorCode(205);
        errorResponse.setErrors(errors);
        errorResponse.setUri(request.getDescription(false));
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
