package com.example.novel_app.service;

import com.example.novel_app.model.Novel;
import com.example.novel_app.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovelAuthorGenreHelperSevice {
    private final NovelRepository novelRepository;

    public void unlinkNovelWithAuthor(int novelId) {
        Novel novel = novelRepository.findById(novelId);
        novel.setAuthorId(null);
        novelRepository.save(novel);
    }

    public void unlinkNovelWithGenre(int novelId) {
        Novel novel = novelRepository.findById(novelId);
        novel.setGenreId(null);
        novelRepository.save(novel);
    }

    public List<Novel> getNovelByGenre(int genreId) {
        return novelRepository.findByGenreId(genreId);
    }

    public List<Novel> getNovelByAuthor(int authorId) {
        return novelRepository.findByAuthorId(authorId);
    }
}
