package com.burgerbuilder.backend.DTO.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorResponseDTO {

    private int errorCode;
    private String message;
    private String uri;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;


    public ErrorResponseDTO(int errorCode, String message, String uri) {
        this.errorCode = errorCode;
        this.message = message;
        this.uri = uri;
        this.timestamp=new Date();
    }
}
