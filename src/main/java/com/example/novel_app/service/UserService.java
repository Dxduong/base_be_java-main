package com.example.novel_app.service;

import com.example.novel_app.dto.response.PaginatedResponse;
import com.example.novel_app.dto.response.UserResponse;
import com.example.novel_app.mapper.UserMapper;
import com.example.novel_app.model.User;
import com.example.novel_app.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public User getUserByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    public PaginatedResponse<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = authRepository.findAll(pageable);
        List<UserResponse> userResponses = userMapper.toUserResponseList(users.getContent());
        
        long totalElements = users.getTotalElements();
        long totalPages = users.getTotalPages();
        int currentPage = pageable.getPageNumber();
        return new PaginatedResponse<>(userResponses, totalElements, currentPage, totalPages);
    }
}