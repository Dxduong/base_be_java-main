package com.example.novel_app.repository;

import com.example.novel_app.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRespository extends JpaRepository<Otp, Integer> {
    Otp findByEmailAndOtpCodeAndStatusOtp(String email,String otpCode,boolean status);
}
