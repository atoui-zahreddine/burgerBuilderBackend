package com.burgerbuilder.backend.Utils.JwtUtils;

import com.burgerbuilder.backend.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private final String CLAIMS_SUBJECT = "sub";
    private final String CLAIMS_CREATED = "created";
    @Value("${auth.expiration}")
    private Long TOKEN_VALIDITY ;

    @Value("${auth.secret}")
    private String SECRET;


    public String generateToken (){

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIMS_SUBJECT,user.getUsername());
        claims.put(CLAIMS_CREATED,new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();

    }
    public String getUsernameFromToken(String token){
        try{
            return getClaims(token).getSubject();
        }
        catch(Exception ex){
            return null;
        }
    }

    private Date generateExpirationDate(){
        return new Date( System.currentTimeMillis() + TOKEN_VALIDITY*1000);
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getClaims(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception ex){
            return null;
        }
    }
}
