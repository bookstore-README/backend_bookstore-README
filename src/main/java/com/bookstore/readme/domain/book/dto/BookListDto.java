package com.bookstore.readme.domain.book.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BookListDto {
    private Integer total;
    private Integer page;
    private Integer limit;
    private Integer cursorId;
    private List<BookDto> reviews;

    @Builder
    public BookListDto(Integer total, Integer page, Integer limit, Integer cursorId, List<BookDto> reviews) {
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.cursorId = cursorId;
        this.reviews = reviews;
    }
}
