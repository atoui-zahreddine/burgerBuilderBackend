package com.burgerbuilder.backend.Exception;


import com.burgerbuilder.backend.DTO.Response.ApiResponse;
import com.burgerbuilder.backend.Utils.Enums.Status;
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
        var response=new ApiResponse<>(Status.FAILURE,Map.of("error:",ex.getMessage()));
        return new ResponseEntity<>(response,ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> errors=ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        var response=new ApiResponse<>(Status.FAILURE,Map.of("errors",errors));

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

}
