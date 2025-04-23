package com.example.novel_app.mapper;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.request.NovelRequest;
import com.example.novel_app.dto.response.NovelResponse;
import com.example.novel_app.model.Novel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NovelMapeper {
    Novel toNovel(NovelRequest novelRequest);

    NovelDTO toNovelDTO(Novel novel);

    List<NovelDTO> toNovelDTOList(List<Novel> novels);
}
