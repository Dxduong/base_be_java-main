package com.example.novel_app.repository;

import com.example.novel_app.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByGenreName(String genreName);
}
