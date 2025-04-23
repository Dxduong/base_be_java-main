package com.example.novel_app.service;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.request.GenreRequest;
import com.example.novel_app.dto.response.GenreResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.GenreMapper;
import com.example.novel_app.model.Author;
import com.example.novel_app.model.Genre;
import com.example.novel_app.model.Novel;
import com.example.novel_app.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final NovelAuthorGenreHelperSevice novelAuthorGenreHelperSevice;

    public GenreResponse getGenreById(int id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            Genre genreEntity = genre.get();
            return genreMapper.toGenreResponse(genreEntity);
        }
        throw new AppException(ErrorCode.GENRE_NOT_EXISTED, HttpStatus.NOT_FOUND);
    }

    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        genres.sort(Comparator.comparing(Genre::getId));
        List<GenreResponse> genresResponse = new ArrayList<>();
        if (genres != null && !genres.isEmpty()) {
            for (Genre genre : genres) {
                genresResponse.add(genreMapper.toGenreResponse(genre));
            }
        }
        return genresResponse;
    }

    public GenreResponse createGenre(GenreRequest genreRequest) {
        Genre genre = genreRepository.findByGenreName(genreRequest.getGenreName());
        if (genre != null) {
            throw new AppException(ErrorCode.GENRE_EXISTED, HttpStatus.CONFLICT);
        }
        Genre genreEntity = genreMapper.toGenre(genreRequest);
        return genreMapper.toGenreResponse(genreRepository.save(genreEntity));
    }

    public GenreResponse updateGenre(int id, GenreRequest genreRequest) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            throw new AppException(ErrorCode.GENRE_NOT_EXISTED, HttpStatus.NOT_FOUND);
        }
        Genre genreEntity = genreOptional.get();
        if (genreRequest.getGenreName() != null) {
            genreEntity.setGenreName(genreRequest.getGenreName());
        }
        Genre genreEntityResult = genreRepository.save(genreEntity);
        return genreMapper.toGenreResponse(genreEntityResult);
    }

    public void deleteGenre(int id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            throw new AppException(ErrorCode.GENRE_NOT_EXISTED, HttpStatus.NOT_FOUND);
        }
        Genre genreEntity = genreOptional.get();
        List<Novel> listNovel = novelAuthorGenreHelperSevice.getNovelByGenre(genreEntity.getId());
        if (listNovel != null || !listNovel.isEmpty()) {
            NovelDTO novelDTO = new NovelDTO();
            novelDTO.setGenreId(0);
            for (Novel novel : listNovel) {
                novelAuthorGenreHelperSevice.unlinkNovelWithGenre(novel.getId());
            }
        }
        genreRepository.delete(genreEntity);
    }

}
