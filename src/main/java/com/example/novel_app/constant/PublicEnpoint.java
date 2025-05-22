package com.example.novel_app.constant;

import org.springframework.stereotype.Component;

@Component
public class PublicEnpoint {
        public final static String[] PUBLIC_ENDPOINTS =
                {
                        "/api/v1/auth/login",
                        "/api/v1/auth/register",
                        "/api/v1/test",
                        "/api/v1/admin/users",
                        "/web/auth/change-password"
                };
        public final static String[] ROLE_ADMIN = {
                "/api/v1/**"
        };
        public final static String[] ROLE_USER = {
                "/api/v1/**"
        };
}
