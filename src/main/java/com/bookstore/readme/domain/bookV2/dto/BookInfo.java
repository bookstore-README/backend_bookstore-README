package com.bookstore.readme.domain.bookV2.dto;

import com.bookstore.readme.domain.bookmark.dto.BookmarkDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BookInfo {
    private final Long bookId;
    private final String bookTitle;
    private final String description;
    private final String publisher;
    private final String bookImgUrl;
    private final Integer price;
    private final List<String> authors;
    private final List<String> categories;
    private final BookStatics statics;
    private final BookmarkDto bookmarks;
    private final LocalDateTime publishedDate;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    @Builder
    public BookInfo(Long bookId, String bookTitle, String description, List<String> authors, List<String> categories, String publisher, String bookImgUrl, Integer price, BookStatics statics, BookmarkDto bookmarks, LocalDateTime publishedDate, LocalDateTime createDate, LocalDateTime updateDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.description = description;
        this.authors = authors;
        this.categories = categories;
        this.publisher = publisher;
        this.bookImgUrl = bookImgUrl;
        this.price = price;
        this.statics = statics;
        this.bookmarks = bookmarks;
        this.publishedDate = publishedDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
