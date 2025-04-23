package com.example.novel_app.constant;

import org.springframework.stereotype.Component;

@Component
public class PublicEnpoint {
    public final static String[] PUBLIC_ENDPOINTS =
            {
                    "/api/v1/auth/login",
                    "/api/v1/auth/register",
                    "/api/v1/sendmail",
                    "/api/v1/test",
                    "/api/v1/email",
                    "/api/v1/aes",
                    "/api/v1/aes/{id}",
                    "/api/v1/otp/authorizeOtp",
                    "/api/v1/otp/createOtp",
//                    "/api/v1/admin/users",
                    "/web/auth/change-password"
            };
    public final static String[] ROLE_ADMIN = {
            "/api/v1/novel/**"
    };
    public final static String[] ROLE_USER = {
            "/api/v1/aes/**"
    };
}
