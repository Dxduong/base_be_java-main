package com.example.novel_app.controller;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.request.OtpAuthorizeRequest;
import com.example.novel_app.dto.request.OtpRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.service.OtpService;
import com.example.novel_app.utils.AesEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "otp")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OtpController {

    private final OtpService otpService;
    @PostMapping("/createOtp")
    public ResponseEntity<ApiResponse> createOtp(@RequestBody OtpRequest otpRequest) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.CREATE_OTP.getMessage());
        otpService.sendOTP(otpRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PostMapping("/authorizeOtp")
    public ResponseEntity<ApiResponse> authorizeOtp(@RequestBody OtpAuthorizeRequest otpAuthorizeRequest) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.AUTHORIZE_OTP.getMessage());
        otpService.authorizeOtp(otpAuthorizeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
