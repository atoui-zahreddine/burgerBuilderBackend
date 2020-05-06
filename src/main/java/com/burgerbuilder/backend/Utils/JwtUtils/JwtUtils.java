package com.burgerbuilder.backend.Utils.JwtUtils;

import com.burgerbuilder.backend.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${auth.expiration}")
    private Long TOKEN_VALIDITY ;

    @Value("${auth.secret}")
    private String SECRET;


    public String generateToken (User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims=getClaims(token);
        return claims!=null?claims.getSubject():null;
    }

    private Date generateExpirationDate(){
        return new Date( System.currentTimeMillis() + TOKEN_VALIDITY*1000);
    }

    private Claims getClaims(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException ex){
            return null;
        }
    }
}
