package com.example.novel_app.mapper;

import com.example.novel_app.dto.request.AuthorRequest;
import com.example.novel_app.dto.response.AuthorResponse;
import com.example.novel_app.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse toAuthorResponse(Author author);

    Author toAuthor(AuthorRequest authorRequest);
}
