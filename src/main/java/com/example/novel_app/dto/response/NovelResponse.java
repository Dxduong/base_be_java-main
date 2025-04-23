package com.example.novel_app.dto.response;

import com.example.novel_app.model.Author;
import com.example.novel_app.model.Genre;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelResponse {
    int id;
    AuthorResponse author;
    GenreResponse genre;
    String title;
    String description;
    String imageUrl;
    String status;
    int rate;
}
