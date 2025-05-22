package com.example.novel_app.controller.auth;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.ChangePasswordRequest;
import com.example.novel_app.dto.request.LoginRequest;
import com.example.novel_app.dto.request.UpdateInforRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.service.AuthentiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {
    private final AuthentiationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountDTO>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<AccountDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.LOGIN.getMessage());
        apiResponse.setData(authenticationService.login(loginRequest));
        return ResponseEntity.status(HttpStatus.valueOf(apiResponse.getCode())).body(apiResponse);
    }
    @PostMapping("/update_password")
    public ResponseEntity<ApiResponse<AccountDTO>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        ApiResponse<AccountDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.UPDATE_PASS.getMessage());
        apiResponse.setData(authenticationService.changePassword(changePasswordRequest));
        return ResponseEntity.status(HttpStatus.valueOf(apiResponse.getCode())).body(apiResponse);
    }
    @PostMapping("/update_infor")
    public ResponseEntity<ApiResponse<AccountDTO>> updateInfor(@RequestBody UpdateInforRequest updateInforRequest) {
        ApiResponse<AccountDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.UPDATE_INFOR.getMessage());
        apiResponse.setData(authenticationService.updateInfor(updateInforRequest));
        return ResponseEntity.status(HttpStatus.valueOf(apiResponse.getCode())).body(apiResponse);
    }



}