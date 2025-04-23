package com.example.novel_app.mapper;

import com.example.novel_app.dto.request.CommentRequest;
import com.example.novel_app.dto.response.CommentResponse;
import com.example.novel_app.model.CommentNovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentNovel toCommentNovel(CommentRequest commentRequest);
    CommentResponse toCommentResponse(CommentNovel commentNovel);
}
