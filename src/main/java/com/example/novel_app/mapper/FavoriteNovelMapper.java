package com.example.novel_app.mapper;

import com.example.novel_app.dto.request.FavoriteNovelRequest;
import com.example.novel_app.dto.response.FavoriteNovelResponse;
import com.example.novel_app.model.FavoriteNovel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteNovelMapper {
    FavoriteNovel toFavoriteNovel(FavoriteNovelRequest favoriteNovelRequest);
    @Mapping(source = "statusFavorite", target = "status" )
    FavoriteNovelResponse toFavoriteNovelResponse(FavoriteNovel favoriteNovel);

    List<FavoriteNovelResponse> toListFavoriteNovelResponse(List<FavoriteNovel> favoriteNovelList);

}
