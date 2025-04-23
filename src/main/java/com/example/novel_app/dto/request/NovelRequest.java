package com.example.novel_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelRequest {
    int id;
    int authorId;
    int genreId;
    String title;
    String description;
    String imageUrl;
    String status;
    int rate;
}
