package com.stussy.stussy.clone20220929ng.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String secretKey = "1234";

    public String createToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofMinutes(30).toMillis());

        return "Bearer " + Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("nage")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject(subject)
                .claim("email", "nage@")
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    public Claims parseJwtToken(String token) {
        validationAuthorizationHeader(token);
        token = bearerRemove(token);

        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    private void validationAuthorizationHeader(String token) {
         if(token == null || !token.startsWith("Authorization")){
             throw new IllegalArgumentException();
         }

    }

    private String bearerRemove(String token) {

        return token.substring("Bearer ".length());
    }


}
