package com.stussy.stussy.clone20220929ng.controller.api;

import com.stussy.stussy.clone20220929ng.util.JwtProvider;
import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class JWTApi {

    private final JwtProvider jwtProvider;

    @PostMapping("/jwt/{username}")
    public ResponseEntity<?> createJwt(@PathVariable String username) { // 로그인 시 토큰 하나를 발급하여 준다.
//        Date now = new Date();
//
//        String jwt = Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입 JWT 로 잡아라
//                .setIssuer("nk") // 발급자 -> 사이트명
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // 만료시간
//                .claim("username", "nage")
//                .claim("email", "nage@")
//                .signWith(SignatureAlgorithm.HS256, "1234")
//                .compact();
//
//
//        jwt = "Bearer " + jwt;

        String token = jwtProvider.createToken(username);
        Claims claim = jwtProvider.parseJwtToken(token);

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully jwt created", token));
    }

    @GetMapping("/jwt")
    public ResponseEntity<?> checkToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {

        Claims claim = jwtProvider.parseJwtToken(token);
        return ResponseEntity.ok(new CMRespDto<>(1, "Get Token", token));
    }

}
