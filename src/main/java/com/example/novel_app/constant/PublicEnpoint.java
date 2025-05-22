package com.example.novel_app.constant;

import org.springframework.stereotype.Component;

@Component
public class PublicEnpoint {
        public final static String[] PUBLIC_ENDPOINTS =
                {
                        "/auth/register",
                        "/auth/login",
                        "/test/db-connection",
                        "/admin/users",
                        "/web/auth/change-password",
                        
                };
        // public final static String[] ROLE_ADMIN = {
        //         "/api/v1/**"
        // };
        // public final static String[] ROLE_USER = {
        //         "/api/v1/**"
        // };
}
