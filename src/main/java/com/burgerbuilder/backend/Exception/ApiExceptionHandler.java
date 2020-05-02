package com.burgerbuilder.backend.Exception;


import com.burgerbuilder.backend.DTO.Response.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<?> handleApiException (ApiBaseException ex , WebRequest request){
        ErrorResponseDTO errorResponseDTO =new ErrorResponseDTO(ex.getErrorCode(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorResponseDTO,ex.getHttpStatus());
    }

}
