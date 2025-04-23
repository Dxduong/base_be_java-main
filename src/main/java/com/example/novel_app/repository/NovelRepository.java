package com.example.novel_app.repository;

import com.example.novel_app.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NovelRepository  extends JpaRepository<Novel, Integer> {
    Optional<Novel> findByTitle(String title);
    Novel findById(int id);
    @Query("SELECT n FROM Novel n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Novel> search(String title);
    List<Novel> findByGenreId(int genreId);
    List<Novel> findByAuthorId(int authorId);
}
