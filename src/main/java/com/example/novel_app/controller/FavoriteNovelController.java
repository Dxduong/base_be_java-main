package com.example.novel_app.controller;

import com.example.novel_app.dto.request.FavoriteNovelRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.FavoriteNovelResponse;
import com.example.novel_app.service.FavoriteNovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "favorite_novel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteNovelController {
    private final FavoriteNovelService favoriteNovelService;
    @PostMapping
    public ResponseEntity<ApiResponse> changeStatusFavoriteNovelToUser(@RequestBody FavoriteNovelRequest favoriteNovelRequest) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        favoriteNovelService.changeStatusFavoriteNovelToUser(favoriteNovelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteNovelResponse>>> getListFavoriteNovelByUserId(@RequestParam int userId) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        apiResponse.setData(favoriteNovelService.getListFavoriteNovelByUser(userId));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("status")
    public ResponseEntity<ApiResponse<FavoriteNovelResponse>> getStatusFavoriteByUserIdAndNovelId(@RequestParam int userId, @RequestParam int novelId) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        FavoriteNovelRequest favoriteNovelRequest = new FavoriteNovelRequest(novelId, userId);
        apiResponse.setData(favoriteNovelService.getFavoriteNovelFromUserByNovelAndUserId(favoriteNovelRequest));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
