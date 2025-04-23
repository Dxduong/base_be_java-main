package com.example.novel_app.dto.response;

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
public class NovelChapterResponse {
    int id;
    int authorId;
    int genreId;
    String title;
    String description;
    String imageUrl;
    String status;
    List<ChapterResponse> listChapter;
}
