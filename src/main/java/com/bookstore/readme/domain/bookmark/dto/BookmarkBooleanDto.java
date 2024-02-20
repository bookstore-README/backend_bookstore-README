package com.bookstore.readme.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkBooleanDto {
    private final Long bookmarkId;
    private final boolean isMarked;

    @Builder
    public BookmarkBooleanDto(Long bookmarkId, boolean isMarked) {
        this.bookmarkId = bookmarkId;
        this.isMarked = isMarked;
    }
}
