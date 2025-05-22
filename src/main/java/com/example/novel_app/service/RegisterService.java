package com.example.novel_app.service;

import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.RegisterRequest;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.UserMapper;
import com.example.novel_app.model.User;
import com.example.novel_app.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterService {
    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public AccountDTO registerUser(RegisterRequest registerRequest) {
        User user = authRepository.findByEmail(registerRequest.getEmail());
        if (user != null) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        
        User newUser = new User();
        newUser.setFullName(registerRequest.getFullName());
        newUser.setPassword(registerRequest.getPassword()); // Lưu mật khẩu dạng plaintext
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRoles(Set.of("USER"));
        
        User savedUser = authRepository.save(newUser);
        return userMapper.toAccountDTO(savedUser);
    }

    public boolean findEmailExisting(String email) {
        User user = authRepository.findByEmail(email);
        if (user != null) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        return false;
    }
}