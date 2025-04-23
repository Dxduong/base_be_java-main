package com.example.novel_app.service;

import com.example.novel_app.dto.request.FavoriteNovelRequest;
import com.example.novel_app.dto.response.FavoriteNovelResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.FavoriteNovelMapper;
import com.example.novel_app.model.FavoriteNovel;
import com.example.novel_app.repository.FavoriteNovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteNovelService {
    private final FavoriteNovelRepository favoriteNovelRepository;
    private final FavoriteNovelMapper favoriteNovelMapper;

    public void changeStatusFavoriteNovelToUser(FavoriteNovelRequest favoriteNovelRequest) {
        if (favoriteNovelRequest.getNovelId() == 0) {
            throw new AppException(ErrorCode.NOVEL_NOT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        if (favoriteNovelRequest.getUserId() == 0) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
        FavoriteNovel favoriteNovelExisting =
                favoriteNovelRepository.findByUserIdAndNovelId(favoriteNovelRequest.getUserId(), favoriteNovelRequest.getNovelId());
        if (favoriteNovelExisting != null) {
            favoriteNovelExisting.setStatusFavorite(!favoriteNovelExisting.isStatusFavorite());
            favoriteNovelRepository.save(favoriteNovelExisting);
        } else {
            FavoriteNovel favoriteNovel = favoriteNovelMapper.toFavoriteNovel(favoriteNovelRequest);
            favoriteNovel.setStatusFavorite(true);
            favoriteNovelRepository.save(favoriteNovel);
        }
    }

    public FavoriteNovelResponse getFavoriteNovelFromUserByNovelAndUserId(FavoriteNovelRequest favoriteNovelRequest
    ) {
        FavoriteNovel favoriteNovel =
                favoriteNovelRepository.findByUserIdAndNovelId(favoriteNovelRequest.getUserId(),
                        favoriteNovelRequest.getNovelId());
        if (favoriteNovel == null) {
            return new FavoriteNovelResponse(favoriteNovelRequest.getUserId(),
                    favoriteNovelRequest.getNovelId(), false);
        }
        return favoriteNovelMapper.toFavoriteNovelResponse(favoriteNovel);
    }

    public List<FavoriteNovelResponse> getListFavoriteNovelByUser(int userId) {
        List<FavoriteNovel> favoriteNovelResponseList =
                favoriteNovelRepository.findByUserIdAndStatusFavorite(userId, true);
        return favoriteNovelMapper.toListFavoriteNovelResponse(favoriteNovelResponseList);
    }


}
