package com.bookstore.readme.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkDto {

    private final Long bookId;
    private final Long memberId;
    private final boolean isMarked;

    @Builder
    public BookmarkDto(Long bookId, Long memberId, boolean isMarked) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.isMarked = isMarked;
    }

}
