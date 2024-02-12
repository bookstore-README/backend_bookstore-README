package com.bookstore.readme.domain.community.dto;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommunityBookDto {
    private final Long bookId;
    private final String bookImgUrl;
    private final String bookTitle;

    @Builder
    public CommunityBookDto(Long bookId, String bookImgUrl, String bookTitle) {
        this.bookId = bookId;
        this.bookImgUrl = bookImgUrl;
        this.bookTitle = bookTitle;
    }

    public static CommunityBookDto of(Book book) {
        return CommunityBookDto.builder()
                .bookId(book.getId())
                .bookImgUrl(book.getBookImgUrl())
                .bookTitle(book.getBookTitle())
                .build();
    }
}
