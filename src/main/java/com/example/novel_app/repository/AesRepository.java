package com.example.novel_app.repository;

import com.example.novel_app.model.TestAes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AesRepository extends JpaRepository<TestAes, Integer> {
}
