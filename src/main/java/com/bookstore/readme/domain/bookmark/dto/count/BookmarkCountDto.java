package com.bookstore.readme.domain.bookmark.dto.count;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookmarkCountDto {
    private final Integer bookmarkCount;

    public BookmarkCountDto(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }
}
