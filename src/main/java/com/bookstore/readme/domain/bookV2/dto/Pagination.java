package com.bookstore.readme.domain.bookV2.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Pagination {
    private final Integer total;
    private final Integer limit;
    private final Integer offset;
    private final List<BookInfo> books;

    @Builder
    public Pagination(Integer total, Integer limit, Integer offset, List<BookInfo> books) {
        this.total = total;
        this.limit = limit;
        this.offset = offset;
        this.books = books;
    }
}
