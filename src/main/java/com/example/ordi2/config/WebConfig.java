package com.example.ordi2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // your React/Vite frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Profile images
                registry.addResourceHandler("/profile-images/**")
                        .addResourceLocations("file:/home/lucas/Dev/Java EE/ORDI-Backend/src/main/resources/profile-images/")
                        .setCachePeriod(0)
                        .resourceChain(true);

                // Recipe images
                registry.addResourceHandler("/api/images/view/**")
                        .addResourceLocations("file:/home/lucas/Dev/Java EE/ORDI-Backend/src/main/resources/receipe-images/")
                        .setCachePeriod(0)
                        .resourceChain(true);

                // Recipe videos
                registry.addResourceHandler("/api/videos/view/**")
                        .addResourceLocations("file:/home/lucas/Dev/Java EE/ORDI-Backend/src/main/resources/receipe-video/")
                        .setCachePeriod(0)
                        .resourceChain(true);
            }
        };
    }
}
