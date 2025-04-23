package com.example.novel_app.repository;

import com.example.novel_app.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByNovelId(int novelId);
    List<Chapter> findByChapterNumberAndNovelId(int chapterNumber ,int novelId);
}
