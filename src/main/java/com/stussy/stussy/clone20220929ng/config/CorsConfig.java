package com.stussy.stussy.clone20220929ng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();;
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 자격증명 허용 설정, Cors 를 풀겠다 . .
        config.addAllowedOrigin("*"); // 모든 요청 주소에 대해 . . .
        config.addAllowedHeader("*"); // 모든 헤더 내용 . .
        config.addAllowedMethod("*"); // 모든  메소드 방식 . . .
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
