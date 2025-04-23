package com.example.novel_app.controller;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.request.AuthorRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.AuthorResponse;
import com.example.novel_app.dto.response.GenreResponse;
import com.example.novel_app.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "author")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthor(@RequestParam(required = false) String name) {
        ApiResponse<List<AuthorResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.GET_ALL_AUTHOR.getMessage());
        List<AuthorResponse> listAuthorResponse;
        if (name == null) {
            listAuthorResponse = authorService.getAllAuthors();
        } else {
            listAuthorResponse = authorService.searchByAuthorName(name);
        }
        apiResponse.setData(listAuthorResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
//    @GetMapping(name = "search")
//    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAllAuthorByName(@RequestParam String name) {
//        ApiResponse<List<AuthorResponse>> apiResponse = new ApiResponse<>();
//        apiResponse.setCode(HttpStatus.OK.value());
//        apiResponse.setMessage(SuccessMessage.SEARCH_AUTHOR_BY_NAME.getMessage());
//        List<AuthorResponse> listAuthorResponse = authorService.searchByAuthorName(name);
//        apiResponse.setData(listAuthorResponse);
//        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
//    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getGenreById(@PathVariable int id) {
        ApiResponse<AuthorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.TAKE_AUTHOR.getMessage());
        AuthorResponse authorResponse = authorService.getAuthorById(id);
        apiResponse.setData(authorResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@RequestBody AuthorRequest authorRequest) {
        ApiResponse<AuthorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.CREATE_AUTHOR.getMessage());
        AuthorResponse authorResponse = authorService.createAuthor(authorRequest);
        apiResponse.setData(authorResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>>
    updateAuthorById(@PathVariable int id,
                    @RequestBody AuthorRequest authorRequest) {
        ApiResponse<AuthorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.UPDATE_AUTHOR.getMessage());
        AuthorResponse authorResponse = authorService.updateAuthor(id, authorRequest);
        apiResponse.setData(authorResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteAuthorById(@PathVariable int id) {
        ApiResponse<GenreResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.DELETE_AUTHOR.getMessage());
        authorService.deleteAuthorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }
}
