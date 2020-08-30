package com.burgerbuilder.backend.Security;

import com.burgerbuilder.backend.DTO.Response.ErrorResponse;
import com.burgerbuilder.backend.Exception.ApiBaseException;
import com.burgerbuilder.backend.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }catch (ApiBaseException ex){
            ErrorResponse errorResponse=new ErrorResponse(
                    240, Map.of("error",ex.getMessage()),request.getRequestURI());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(Utils.convertObjectToJson(errorResponse));
        }
    }


}
