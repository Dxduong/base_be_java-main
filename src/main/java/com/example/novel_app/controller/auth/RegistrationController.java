package com.example.novel_app.controller.auth;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.RegisterRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.service.RegisterService;
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
public class RegistrationController {
    private final RegisterService registerService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountDTO>> register(@RequestBody RegisterRequest registerRequest) {
        ApiResponse<AccountDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.REGISTER.getMessage());
        apiResponse.setData(registerService.registerUser(registerRequest));
        return ResponseEntity.status(HttpStatus.valueOf(apiResponse.getCode())).body(apiResponse);
    }
}
