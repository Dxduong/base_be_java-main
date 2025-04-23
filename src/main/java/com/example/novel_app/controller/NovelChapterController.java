package com.example.novel_app.controller;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.service.NovelChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "novel_chapter")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovelChapterController {
    private final NovelChapterService novelChapterService;

    @GetMapping("{novelId}")
    public ResponseEntity<ApiResponse<NovelDTO>> getNovelChapter(@PathVariable int novelId,
                                                                 @RequestParam(required = false) Integer chapterNumber) {
        ApiResponse<NovelDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        NovelDTO novelDTO;
        if (chapterNumber != null) {
            novelDTO = novelChapterService.getChapterByNovelAndByChapterNumber(novelId, chapterNumber);
        } else {
            novelDTO = novelChapterService.getChapterByNovel(novelId);
        }
        apiResponse.setData(novelDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
