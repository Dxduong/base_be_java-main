package com.example.novel_app.utils;

import java.security.SecureRandom;

public class OtpGenerator {
    public static String generateOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random number between 0 and 9
            otp.append(digit);
        }

        return otp.toString();
    }
}
