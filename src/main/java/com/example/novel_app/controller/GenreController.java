package com.example.novel_app.controller;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.request.GenreRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.GenreResponse;
import com.example.novel_app.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "genre")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAllGenres() {
        ApiResponse<List<GenreResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.GET_ALL_GENRE.getMessage());
        List<GenreResponse> listGenreResponse = genreService.getAllGenres();
        apiResponse.setData(listGenreResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenreById(@PathVariable int id) {
        ApiResponse<GenreResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.TAKE_GENRE.getMessage());
        GenreResponse genreResponse = genreService.getGenreById(id);
        apiResponse.setData(genreResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(@RequestBody GenreRequest genreRequest) {
        ApiResponse<GenreResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.CREATE_GENRE.getMessage());
        GenreResponse genreResponse = genreService.createGenre(genreRequest);
        apiResponse.setData(genreResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<GenreResponse>>
    updateGenreById(@PathVariable int id,
                    @RequestBody GenreRequest genreRequest) {
        ApiResponse<GenreResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.UPDATE_GENRE.getMessage());
        GenreResponse genreResponse = genreService.updateGenre(id, genreRequest);
        apiResponse.setData(genreResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteGenreById(@PathVariable int id) {
        ApiResponse<GenreResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.DELETE_GENRE.getMessage());
        genreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }


}
