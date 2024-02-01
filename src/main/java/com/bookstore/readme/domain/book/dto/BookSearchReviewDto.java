package com.bookstore.readme.domain.book.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookSearchReviewDto extends BookDto {
    private List<ReviewSearchDto> reviews;

    public BookSearchReviewDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Integer bookMarked, Double averageRating, Integer price, LocalDateTime createDate, LocalDateTime updateDate, List<ReviewSearchDto> reviews) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, bookMarked, averageRating, price, createDate, updateDate);
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
                .bookMarked(book.getBookmarked())
                .averageRating(book.getAverageRating())
                .price(book.getPrice())
                .reviews(reviews)
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }
}
