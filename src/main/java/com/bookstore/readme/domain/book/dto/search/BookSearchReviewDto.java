package com.bookstore.readme.domain.book.dto.search;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookSearchReviewDto extends BookDto {
    private List<ReviewSearchDto> reviews;

    public BookSearchReviewDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Double averageRating, Integer price, Integer bookmarkCount, Integer reviewCount, Integer viewCount, String publisher, LocalDateTime createDate, LocalDateTime updateDate, List<ReviewSearchDto> reviews) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, averageRating, price, bookmarkCount, reviewCount, viewCount, publisher, createDate, updateDate);
        this.reviews = reviews;
    }

    public static BookSearchReviewDto of(Book book, List<ReviewSearchDto> reviews) {
        return BookSearchReviewDto.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .publishedDate(book.getPublishedDate())
                .bookImgUrl(book.getBookImgUrl())
                .authors(convertAuthors(book.getAuthors()))
                .description(book.getDescription())
                .categories(convertCategories(book.getCategories()))
                .averageRating(book.getAverageRating())
                .reviews(reviews)
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