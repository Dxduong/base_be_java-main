package com.example.novel_app.filter;

import com.example.novel_app.constant.PublicEnpoint;
import com.example.novel_app.dto.request.IntroSpectRequest;
import com.example.novel_app.dto.response.IntrospectResponse;
import com.example.novel_app.model.User;
import com.example.novel_app.service.AuthenticationService;
import com.example.novel_app.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String uri = request.getRequestURI();
            if (!httpRequest.isSecure()) {
                // Nếu yêu cầu là HTTP, bỏ qua xác thực và tiếp tục filter chain
                filterChain.doFilter(request, response);
                return;
            }
            // Kiểm tra nếu URI trùng khớp với một trong các endpoint công khai
            for (String endpoint : PublicEnpoint.PUBLIC_ENDPOINTS) {
                if (uri.startsWith(endpoint)) {
                    filterChain.doFilter(request, response);  // Tiếp tục yêu cầu mà không kiểm tra JWT
                    return;
                }
            }
            // Lấy token từ header
            String jwt = parseJwt(request);
            System.out.println("Value of token : " + jwt);
            if (jwt == null || jwt.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            IntroSpectRequest introSpectRequest = new IntroSpectRequest();
            introSpectRequest.setToken(jwt);
            IntrospectResponse introspectResponse = authenticationService.verifyToken(introSpectRequest);
            boolean verifyToken = introspectResponse.isValid();
            if (verifyToken) {
                // Lấy username từ token

                String email = authenticationService.getEmailFromToken(introSpectRequest.getToken());
                List<String> scope = authenticationService.getScopesFromToken(introSpectRequest.getToken());
                User user = userService.getUserByEmail(email);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                System.out.println("Authen success");
                List<GrantedAuthority> authorities = scope.stream()
                        .map(role -> "ROLE_" + role)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        } catch (Exception e) {
            System.err.println("Không thể xác thực người dùng: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Loại bỏ "Bearer "
        }
        return null;
    }
}
