package com.example.novel_app.service;

import com.example.novel_app.dto.NovelDTO;
import com.example.novel_app.dto.request.AuthorRequest;
import com.example.novel_app.dto.response.AuthorResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.AuthorMapper;
import com.example.novel_app.model.Author;
import com.example.novel_app.model.Novel;
import com.example.novel_app.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final NovelAuthorGenreHelperSevice novelAuthorGenreHelperSevice;

    public AuthorResponse getAuthorById(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            Author authorEntity = author.get();
            return authorMapper.toAuthorResponse(authorEntity);
        }
        throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED, HttpStatus.NOT_FOUND);
    }

    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.sort(Comparator.comparing(Author::getId));
        List<AuthorResponse> genresResponse = new ArrayList<>();
        if (authors != null && !authors.isEmpty()) {
            for (Author author : authors) {
                genresResponse.add(authorMapper.toAuthorResponse(author));
            }
        }
        return genresResponse;
    }

    public List<AuthorResponse> searchByAuthorName(String name) {
        List<Author> authors = authorRepository.findByAuthorNameContaining(name);
        List<AuthorResponse> genresResponse = new ArrayList<>();
        if (authors != null && !authors.isEmpty()) {
            for (Author author : authors) {
                genresResponse.add(authorMapper.toAuthorResponse(author));
            }
        }
        return genresResponse;
    }





    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        Author author = authorRepository.findByAuthorName(authorRequest.getAuthorName());
        if (author != null) {
            throw new AppException(ErrorCode.AUTHOR_EXISTED, HttpStatus.CONFLICT);
        }
        Author authorEntity = authorMapper.toAuthor(authorRequest);
        authorEntity = authorRepository.save(authorEntity);
        return authorMapper.toAuthorResponse(authorEntity);
    }

    public AuthorResponse updateAuthor(int id, AuthorRequest authorRequest) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (!authorOptional.isPresent()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED, HttpStatus.NOT_FOUND);
        }
        Author author = authorOptional.get();

        if (authorRequest.getAuthorName() != null) {
            author.setAuthorName(authorRequest.getAuthorName());
        }
        if (authorRequest.getBio() != null) {
            author.setBio(authorRequest.getBio());
        }
        if (authorRequest.getBirthDate() != null) {
            author.setBirthDate(authorRequest.getBirthDate());
        }
        return authorMapper.toAuthorResponse(authorRepository.save(author));

    }

    public void deleteAuthorById(int id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (!authorOptional.isPresent()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED, HttpStatus.NOT_FOUND);
        }
        Author authorEntity = authorOptional.get();
        List<Novel> listNovel = novelAuthorGenreHelperSevice.getNovelByGenre(authorEntity.getId());
        if (listNovel != null || !listNovel.isEmpty()) {
            NovelDTO novelDTO = new NovelDTO();
            novelDTO.setAuthorId(0);
            for (Novel novel : listNovel) {
                novelAuthorGenreHelperSevice.unlinkNovelWithAuthor(novel.getId());
            }
        }
        authorRepository.delete(authorEntity);


    }
}
