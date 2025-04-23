package com.example.novel_app.controller;

import com.example.novel_app.dto.request.CommentRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.CommentResponse;
import com.example.novel_app.dto.response.GenreResponse;
import com.example.novel_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "comment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {
    private final CommentService commentService;
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(CommentRequest commentRequest) {
        ApiResponse<CommentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
