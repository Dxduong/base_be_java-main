package com.example.novel_app.repository;

import com.example.novel_app.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByAuthorName(String name);
    List<Author> findByAuthorNameContaining(String name);
}
