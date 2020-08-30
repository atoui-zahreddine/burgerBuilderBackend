package com.burgerbuilder.backend.Exception;

import com.burgerbuilder.backend.DTO.Response.ErrorResponse;
import com.burgerbuilder.backend.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
            var errorResponse =new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                                    Map.of("error","you dont have access ."),
                                    request.getRequestURI());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(Utils.convertObjectToJson(errorResponse));
    }
}
