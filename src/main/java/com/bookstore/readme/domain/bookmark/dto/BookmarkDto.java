package com.bookstore.readme.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkDto {

    private final Long bookmarkId;
    private final Long bookId;
    private final Long memberId;
    private final boolean isMarked;

    @Builder
    public BookmarkDto(Long bookmarkId, Long bookId, Long memberId, boolean isMarked) {
        this.bookmarkId = bookmarkId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.isMarked = isMarked;
    }

}
