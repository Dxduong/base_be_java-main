package com.example.novel_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // Cho phép tất cả các origin (trong môi trường development)
        corsConfiguration.addAllowedOrigin("*");
        
        // Hoặc nếu bạn muốn giới hạn chỉ cho Android Emulator và thiết bị thật
        corsConfiguration.addAllowedOrigin("http://192.168.85.23"); // Android Emulator
        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // Kết nối qua USB debugging
        // corsConfiguration.addAllowedOrigin("http://<your-local-ip>"); // Kết nối qua mạng LAN
        
        corsConfiguration.addAllowedMethod("*"); // Cho phép tất cả HTTP methods
        corsConfiguration.addAllowedHeader("*"); // Cho phép tất cả headers
        corsConfiguration.setAllowCredentials(true); // Cho phép credentials (nếu cần)
        corsConfiguration.addExposedHeader("Authorization"); // Expose Authorization header
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Áp dụng cho tất cả các route

        return new CorsFilter(source);
    }
}