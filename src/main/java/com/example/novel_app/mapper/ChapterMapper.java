package com.example.novel_app.mapper;

import com.example.novel_app.dto.response.ChapterResponse;
import com.example.novel_app.model.Chapter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterResponse toResponse(Chapter chapter);

    List<ChapterResponse> toResponses(List<Chapter> chapters);
}
