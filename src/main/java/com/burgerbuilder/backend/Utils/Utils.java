package com.burgerbuilder.backend.Utils;

import com.burgerbuilder.backend.DTO.Response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String convertObjectToJson(ErrorResponse errorResponse) throws JsonProcessingException {
        if (errorResponse == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(errorResponse);
    }
}
