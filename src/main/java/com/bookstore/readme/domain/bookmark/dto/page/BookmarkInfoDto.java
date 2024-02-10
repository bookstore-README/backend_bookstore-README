package com.bookstore.readme.domain.bookmark.dto.page;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.dto.search.BookDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookmarkInfoDto extends BookDto {
    private final Long bookmarkId;

    public static BookmarkInfoDto of(Book book, Long bookmarkId) {
        return BookmarkInfoDto.builder()
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
                .bookmarkId(bookmarkId)
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

}
