package com.bookstore.readme.domain.bookmark.dto;

import com.bookstore.readme.domain.book.dto.page.BookDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookmarkAndBookDto {
    private final Long memberId;
    private final Integer bookmarkCount;
    private final List<BookDto> books;

    @Builder
    public BookmarkAndBookDto(Long memberId, Integer bookmarkCount, List<BookDto> books) {
        this.memberId = memberId;
        this.bookmarkCount = bookmarkCount;
        this.books = books;
    }
}
