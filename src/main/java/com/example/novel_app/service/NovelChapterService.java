package com.example.novel_app.service;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.response.ChapterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovelChapterService {
    private final NovelService novelService;
    private final ChapterService chapterService;

    public NovelDTO getChapterByNovel(int novelId) {
        NovelDTO novelDTO = novelService.getById(novelId);
        List<ChapterResponse> listChapterResponse =
                chapterService.getChapterByNovel(novelDTO.getId());
        novelDTO.setListChapter(listChapterResponse);
        return novelDTO;
    }
    public NovelDTO getChapterByNovelAndByChapterNumber(int novelId, int chapterNumber){
        NovelDTO novelDTO = novelService.getById(novelId);
        List<ChapterResponse> listChapterResponse =
                chapterService.getChapterByChapterNumber(chapterNumber, novelDTO.getId());
        novelDTO.setListChapter(listChapterResponse);
        return novelDTO;
    }
}
