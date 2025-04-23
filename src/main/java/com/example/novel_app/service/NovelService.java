package com.example.novel_app.service;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.request.NovelRequest;
import com.example.novel_app.dto.response.NovelResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.NovelMapeper;
import com.example.novel_app.model.Novel;
import com.example.novel_app.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovelService {
    private final NovelMapeper novelMapeper;
    private final NovelRepository novelRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ChapterService chapterService;

    public NovelDTO createNovel(NovelRequest novelRequest) {
        if (novelRepository.findByTitle(novelRequest.getTitle()).isPresent()) {
            throw new AppException(ErrorCode.NOVEL_EXISTED, HttpStatus.BAD_REQUEST);
        }
        Novel novel = novelMapeper.toNovel(novelRequest);
        Novel result = novelRepository.save(novel);
        return novelMapeper.toNovelDTO(result);
    }

    public List<NovelResponse> getAll() {
        List<Novel> novels = novelRepository.findAll();
        List<NovelResponse> listNovelResponse = new ArrayList<>();
        if (!novels.isEmpty()) {
            for (Novel novel : novels) {
                NovelResponse resultNovelResponse = toNovelResponse(novel);
                listNovelResponse.add(resultNovelResponse);
            }
        }
        return listNovelResponse;
    }

    public List<NovelResponse> getAllByArrayId(List<Integer> novel_ids) {
        List<NovelResponse> listNovelResponse = new ArrayList<>();
        for (Integer novel_id : novel_ids) {
            Novel novel = novelRepository.findById(novel_id).get();
            NovelResponse resultNovelResponse = toNovelResponse(novel);
            listNovelResponse.add(resultNovelResponse);
        }
        return listNovelResponse;
    }

    public NovelDTO getById(int id) {
        Novel novel = novelRepository.findById(id);
        if (novel == null) {
            throw new AppException(ErrorCode.NOVEL_NOT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        NovelDTO novelDTO = novelMapeper.toNovelDTO(novel);
        novelDTO.setCount_chapters(chapterService.getNumberOfChapterByNovel(novel.getId()));
        return novelDTO;
    }

    public NovelResponse updateNovel(int id, NovelDTO novelDTO) {
        Novel existingNovel = novelRepository.findById(id);
        if (existingNovel != null) {
            if (novelDTO.getTitle() != null) {
                existingNovel.setTitle(novelDTO.getTitle());
            }
            Integer authorId = novelDTO.getAuthorId();
            System.out.println("novelId: " + authorId);
            if (authorId != 0) {
                existingNovel.setAuthorId(authorId);
            } else {
                existingNovel.setAuthorId(null);
            }
            Integer genreId = novelDTO.getGenreId();
            System.out.println("GenreId : " + genreId
            );
            if (genreId != 0) {
                existingNovel.setGenreId(genreId);
            } else {
                existingNovel.setGenreId(null);
            }
            if (novelDTO.getDescription() != null) {
                existingNovel.setDescription(novelDTO.getDescription());
            }

            if (novelDTO.getImageUrl() != null) {
                existingNovel.setImageUrl(novelDTO.getImageUrl());
            }
            if (novelDTO.getStatus() != null) {
                existingNovel.setStatus(novelDTO.getStatus());
            }
            Novel resultNovel = novelRepository.save(existingNovel);
            return toNovelResponse(resultNovel);
        } else {
            throw new AppException(ErrorCode.NOVEL_NOT_EXISTED, HttpStatus.BAD_REQUEST);
        }
    }

    public NovelResponse toNovelResponse(Novel novel) {
        NovelResponse novelResponse = new NovelResponse();
        novelResponse.setId(novel.getId());
        novelResponse.setTitle(novel.getTitle());
        novelResponse.setDescription(novel.getDescription());
        novelResponse.setStatus(novel.getStatus());
        novelResponse.setImageUrl(novel.getImageUrl());
        novelResponse.setRate(novel.getRate());
        novelResponse.setAuthor(
                authorService.getAuthorById(novel.getAuthorId()));
        novelResponse.setGenre(genreService.getGenreById(novel.getGenreId()));
        return novelResponse;
    }

    public void deleteNovel(int id) {
        Novel novel = novelRepository.findById(id);
        if (novel == null) {
            throw new AppException(ErrorCode.NOVEL_NOT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        novelRepository.delete(novel);
    }

    public List<NovelResponse> searchNovelsByName(String name) {
        List<Novel> listNovel = novelRepository.search(name);
        List<NovelResponse> listNovelResponse = new ArrayList<>();
        if (!listNovel.isEmpty()) {
            for (Novel novel : listNovel) {
                NovelResponse novelResponse = toNovelResponse(novel);
                listNovelResponse.add(novelResponse);
            }
        }
        return listNovelResponse;
    }

    public List<NovelResponse> getAllByGenreId(int id) {
        List<NovelResponse> listNovelResponse = new ArrayList<>();
        List<Novel> novels = novelRepository.findByGenreId(id);
        if (!novels.isEmpty()) {
            for (Novel novel : novels) {
                NovelResponse resultNovelResponse = toNovelResponse(novel);
                listNovelResponse.add(resultNovelResponse);
            }
        }
        return listNovelResponse;
    }


}
