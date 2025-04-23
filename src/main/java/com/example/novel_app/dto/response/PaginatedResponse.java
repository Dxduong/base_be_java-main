package com.example.novel_app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponse<T> {
    private List<T> content;
    private long totalCount;
    private int currentPage;
    private long totalPage;

    public PaginatedResponse(List<T> content, long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

    public PaginatedResponse(List<T> content,long totalPage, int currentPage) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public PaginatedResponse(List<T> content, long totalCount, long totalPage) {
        this.content = content;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }
}
