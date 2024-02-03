package com.bookstore.readme.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCountDto {
    private final Long id;
    private final Integer bookmarkCount;

    @Builder
    public BookmarkCountDto(Long id, Integer bookmarkCount) {
        this.id = id;
        this.bookmarkCount = bookmarkCount;
    }
}
