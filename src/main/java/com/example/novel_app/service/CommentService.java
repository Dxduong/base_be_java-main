package com.example.novel_app.service;

import com.example.novel_app.dto.request.CommentRequest;
import com.example.novel_app.dto.response.CommentResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.CommentMapper;
import com.example.novel_app.model.CommentNovel;
import com.example.novel_app.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentResponse createComment(CommentRequest commentRequest) {
        CommentNovel commentNovel = commentMapper.toCommentNovel(commentRequest);
        return commentMapper.toCommentResponse(commentRepository.save(commentNovel));
    }
    public CommentResponse updateComment(int commentId, CommentRequest commentRequest) {        CommentNovel existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND));

        existingComment.setContent(commentRequest.getContent());

        CommentNovel updatedComment = commentRepository.save(existingComment);

        return commentMapper.toCommentResponse(updatedComment);
    }

}
