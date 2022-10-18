package com.stussy.stussy.clone20220929ng.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/image/**")    // 해당 경로로 들어오면, 해당 경로에서 찾아라 . . .
                .addResourceLocations("file:///" + filePath)

                .setCachePeriod(60*60) // 캐시 시간

                // 해당 주소로 리소스가 들어가는 것
                .resourceChain(true)
                .addResolver(new PathResourceResolver() { // PathResourceResolver 객체 생성하면서, override . . . 해줌
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8); // URI를 UTF_8로 디코드 해서 가져와야 함. (한글 사용)
                        return super.getResource(resourcePath, location);
                    }
                    // -> 이미지를 불러올 수 있게 된다.
                });
    }
}
