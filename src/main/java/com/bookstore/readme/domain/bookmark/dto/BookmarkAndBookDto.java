package com.bookstore.readme.domain.bookmark.dto;

import com.bookstore.readme.domain.book.dto.page.BookDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookmarkAndBookDto {
    private final Long bookmarkId;
    private final Long memberId;
    private final Integer bookmarkCount;
    private final List<BookmarkDetailDto> books;

    @Builder
    public BookmarkAndBookDto(Long bookmarkId, Long memberId, Integer bookmarkCount, List<BookmarkDetailDto> books) {
        this.bookmarkId = bookmarkId;
        this.memberId = memberId;
        this.bookmarkCount = bookmarkCount;
        this.books = books;
    }
}
