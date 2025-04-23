package com.example.novel_app.mapper;

import com.example.novel_app.dto.request.GenreRequest;
import com.example.novel_app.dto.response.GenreResponse;
import com.example.novel_app.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreResponse toGenreResponse(Genre genre);
    Genre toGenre(GenreRequest genreRequest);
}
