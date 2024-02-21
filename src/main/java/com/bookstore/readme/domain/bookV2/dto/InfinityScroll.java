package com.bookstore.readme.domain.bookV2.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class InfinityScroll {
    private final Integer total;
    private final Integer limit;
    private final Integer cursorId;
    private final List<BookInfo> books;

    @Builder
    public InfinityScroll(Integer total, Integer limit, Integer cursorId, List<BookInfo> books) {
        this.total = total;
        this.limit = limit;
        this.cursorId = cursorId;
        this.books = books;
    }
}
