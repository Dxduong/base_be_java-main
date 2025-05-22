package com.example.novel_app.configuration;

import com.example.novel_app.constant.PublicEnpoint;
import com.example.novel_app.filter.JwtAuthenticationFilter;
import com.example.novel_app.service.AuthenticationService;
import com.example.novel_app.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService, userService);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers(PublicEnpoint.PUBLIC_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class
            )
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((req, res, ex) -> 
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()))
            );
        
        return http.build();
    }
}