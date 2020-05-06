package com.burgerbuilder.backend.Security;

import com.burgerbuilder.backend.Model.User;
import com.burgerbuilder.backend.Service.UserService;
import com.burgerbuilder.backend.Utils.JwtUtils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Value("${auth.header}")
    private String HEADER;
    private final String HEADER_PREFIX="Bearer ";
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private  UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header=request.getHeader(HEADER);
        if(header != null && header.startsWith(HEADER_PREFIX) && SecurityContextHolder.getContext()!=null){
            var token = header.replace(HEADER_PREFIX,"");
            var username=jwtUtils.getUsernameFromToken(token);
            if(username!=null){
                var user=(User) userService.loadUserByUsername(username);
                if(user!=null){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
