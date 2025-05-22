package com.example.novel_app.repository;

import com.example.novel_app.model.FavoriteNovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteNovelRepository extends JpaRepository<FavoriteNovel, Integer> {
    List<FavoriteNovel> findByUserIdAndStatusFavorite(Integer userId, boolean status);
    FavoriteNovel findByUserIdAndNovelId(Integer userId, Integer novelId);

    List<FavoriteNovel> findByUserId(Integer userId);
}
