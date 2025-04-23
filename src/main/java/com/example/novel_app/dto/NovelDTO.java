package com.example.novel_app.dto;

import com.example.novel_app.dto.response.ChapterResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelDTO {
    int id;
    int authorId;
    int genreId;
    String title;
    String description;
    String imageUrl;
    String status;
    int count_chapters;
    List<ChapterResponse> listChapter;
}
