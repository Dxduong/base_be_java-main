package com.example.novel_app.configuration;

import com.example.novel_app.constant.PublicEnpoint;
import com.example.novel_app.filter.JwtAuthenticationFilter;
import com.example.novel_app.service.AuthentiationService;
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

@Configuration
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {
    private final AuthentiationService authenticationService;
    private final UserService userService;

    // tạo jwt bằng cách thêm "mã mật"
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationService, userService);
        http
                .authorizeHttpRequests(request -> request
                                .requestMatchers(PublicEnpoint.PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(requestHttp -> requestHttp.getScheme().equals("http")).permitAll()
//                        .requestMatchers(PublicEnpoint.ROLE_ADMIN).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                // xác thực token trước khi xác thực security
                // của Spring Security
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        })
                );
        ;
        return http.build();
    }
}