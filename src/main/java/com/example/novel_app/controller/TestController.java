package com.example.novel_app.controller;

import com.example.novel_app.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @GetMapping
    public ResponseEntity<ApiResponse.UserDTO> test() {
        ApiResponse.UserDTO user = new ApiResponse.UserDTO();
        user.setUsername("TEST API SUCCESS");
        return ResponseEntity.ok(user);
    }
}
