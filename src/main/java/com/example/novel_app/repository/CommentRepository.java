package com.example.novel_app.repository;

import com.example.novel_app.model.CommentNovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentNovel, Integer> {
    List<CommentNovel> findByNovelId(Integer novelId);
    List<CommentNovel> findByUserId(Integer userId);
}
