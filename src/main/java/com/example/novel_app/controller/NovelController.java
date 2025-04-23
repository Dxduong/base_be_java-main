package com.example.novel_app.controller;

import com.example.novel_app.constant.SuccessMessage;
import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.request.NovelRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.dto.response.NovelResponse;
import com.example.novel_app.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "novel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovelController {
    private final NovelService novelService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<NovelDTO>> create(@RequestBody NovelRequest novelRequest) {
        ApiResponse<NovelDTO> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.CREATE_NOVEL.getMessage());
        apiResponse.setData(novelService.createNovel(novelRequest));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NovelResponse>>> getAll() {
        ApiResponse<List<NovelResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.GET_ALL_NOVEL.getMessage());
        List<NovelResponse> listNovelDTO = novelService.getAll();
        apiResponse.setData(listNovelDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NovelDTO>> getById(@PathVariable int id) {
        ApiResponse<NovelDTO> apiResponse = new ApiResponse<>();
        NovelDTO novelDTO = novelService.getById(id);
        if (novelDTO != null) {
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage(SuccessMessage.TAKE_NOVEL.getMessage());
            apiResponse.setData(novelDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NovelResponse>> updateById(@PathVariable int id,
                                                                 @RequestBody NovelDTO novelDTO) {
        ApiResponse<NovelResponse> apiResponse = new ApiResponse<>();
        NovelResponse updatedNovel = novelService.updateNovel(id, novelDTO);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.UPDATE_NOVEL.getMessage());
        apiResponse.setData(updatedNovel);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteById(@PathVariable int id) {
        ApiResponse<NovelResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.DELETE_NOVEL.getMessage());
        novelService.deleteNovel(id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    //    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NovelResponse>>> searchNovels(@RequestParam(required =
            false) String name, @RequestParam(required = false) Integer genreId) {
        ApiResponse<List<NovelResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage(SuccessMessage.SEARCH_NOVEL_BY_NAME.getMessage());
        List<NovelResponse> searchResults = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            searchResults = novelService.searchNovelsByName(name);

        } else if (genreId != null) {
            searchResults = novelService.getAllByGenreId(genreId);
        }
        apiResponse.setData(searchResults);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/getByArrayId")
    public ResponseEntity<ApiResponse<List<NovelResponse>>> getByArrayId(@RequestBody List<Integer> novelIds) {
        ApiResponse<List<NovelResponse>> apiResponse = new ApiResponse<>();
        List<NovelResponse> searchResults = novelService.getAllByArrayId(novelIds);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        apiResponse.setData(searchResults);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
