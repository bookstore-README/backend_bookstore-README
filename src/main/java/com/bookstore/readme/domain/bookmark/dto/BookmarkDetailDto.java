package com.bookstore.readme.domain.bookmark.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.search.BookDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookmarkDetailDto extends BookDto {
    private final Long bookmarkId;


    public BookmarkDetailDto(Long bookId, String bookTitle, LocalDateTime publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Double averageRating, Integer price, Integer bookmarkCount, Integer reviewCount, Integer viewCount, String publisher, LocalDateTime createDate, LocalDateTime updateDate, Long bookmarkId) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, averageRating, price, bookmarkCount, reviewCount, viewCount, publisher, createDate, updateDate);
        this.bookmarkId = bookmarkId;
    }

    public static BookmarkDetailDto of(Book book, Long bookmarkId) {
        return BookmarkDetailDto.builder()
                .bookmarkId(bookmarkId)
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .publishedDate(book.getPublishedDate())
                .bookImgUrl(book.getBookImgUrl())
                .authors(convertAuthors(book.getAuthors()))
                .description(book.getDescription())
                .categories(convertCategories(book.getCategories()))
                .averageRating(book.getAverageRating())
                .price(book.getPrice())
                .bookmarkCount(book.getBookmarkCount())
                .reviewCount(book.getReviewCount())
                .viewCount(book.getViewCount())
                .publisher(book.getPublisher())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }
}
