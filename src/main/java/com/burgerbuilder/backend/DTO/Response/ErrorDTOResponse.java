package com.burgerbuilder.backend.DTO.Response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ErrorDTOResponse {

    private int errorCode;
    private Map<String,String> errors;
    private String uri;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    public ErrorDTOResponse() {
        this.timestamp=new Date();
    }

    public ErrorDTOResponse(int errorCode, Map<String,String> errors, String uri) {
        this();
        this.errorCode = errorCode;
        this.errors = errors;
        this.uri = uri;
    }
}
