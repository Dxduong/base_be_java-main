package com.example.novel_app.controller;

import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.PaginatedResponse;
import com.example.novel_app.dto.response.UserResponse;
import com.example.novel_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "admin/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {
        ApiResponse<PaginatedResponse<UserResponse>> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, size);
        response.setMessage("success");
        response.setCode(HttpStatus.OK.value());
        PaginatedResponse<UserResponse> userPage = userService.getAllUsers(pageable);
        response.setData(userPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<ApiResponse<ApiResponse.UserDTO>> register(@RequestBody @Valid ApiResponse.UserDTO user) {
        ApiResponse<ApiResponse.UserDTO> response = new ApiResponse<>();
        response.setMessage("success");
        response.setCode(HttpStatus.OK.value());
        response.setData(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
