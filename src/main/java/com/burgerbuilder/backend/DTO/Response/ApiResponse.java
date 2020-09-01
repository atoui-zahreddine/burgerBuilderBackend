package com.burgerbuilder.backend.DTO.Response;

import com.burgerbuilder.backend.Utils.Enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiResponse<T> {
    private Status status;
    //ignore data if it is null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    public ApiResponse() {
        this.timestamp=new Date();
    }

    public ApiResponse(Status status, T data) {
        this();
        this.status = status;
        this.data = data;
    }
}
