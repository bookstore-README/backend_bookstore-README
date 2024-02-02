package com.bookstore.readme.domain.book.dto;

import com.bookstore.readme.domain.book.dto.search.BookDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BookListDto {
    private Integer total;
    private Integer page;
    private Integer limit;
    private Integer cursorId;
    private List<BookDto> books;

    @Builder
    public BookListDto(Integer total, Integer page, Integer limit, Integer cursorId, List<BookDto> books) {
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.cursorId = cursorId;
        this.books = books;
    }
}
