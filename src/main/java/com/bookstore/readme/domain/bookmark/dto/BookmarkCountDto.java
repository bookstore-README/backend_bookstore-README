package com.bookstore.readme.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCountDto {
    private final Long bookId;
    private final Integer bookmarkCount;

    @Builder
    public BookmarkCountDto(Long bookId, Integer bookmarkCount) {
        this.bookId = bookId;
        this.bookmarkCount = bookmarkCount;
    }
}
