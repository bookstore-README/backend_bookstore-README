package com.bookstore.readme.domain.book.dto.page;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BookPageDto {
    private Integer total;
    private Integer limit;
    private Integer cursorId;
    private List<BookDto> books;

    public BookPageDto(Integer total, Integer limit, Integer cursorId, List<BookDto> books) {
        this.total = total;
        this.limit = limit;
        this.cursorId = cursorId;
        this.books = books;
    }
}
