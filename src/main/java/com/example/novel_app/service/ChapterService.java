package com.example.novel_app.service;

import com.example.novel_app.dto.response.ChapterResponse;
import com.example.novel_app.mapper.ChapterMapper;
import com.example.novel_app.model.Chapter;
import com.example.novel_app.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    List<ChapterResponse> getChapterByNovel(int novelId) {
        List<Chapter> lstChapter = chapterRepository.findByNovelId(novelId);
        List<ChapterResponse> lstChapterResponse = chapterMapper.toResponses(lstChapter);
        return lstChapterResponse;
    }

    List<ChapterResponse> getChapterByChapterNumber(int chapterNumber, int novelId) {
        List<Chapter> lstChapter = chapterRepository.findByChapterNumberAndNovelId(chapterNumber,
                novelId);
        List<ChapterResponse> lstChapterResponse = chapterMapper.toResponses(lstChapter);
        return lstChapterResponse;
    }

    int getNumberOfChapterByNovel(int novelId) {
        List<Chapter> lstChapter = chapterRepository.findByNovelId(novelId);
        return lstChapter.size();
    }


}
