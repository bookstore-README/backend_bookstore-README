package com.bookstore.readme.domain.book.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BookDto {
    private final Long bookId;
    private final String bookTitle;
    private final List<ReviewDto> reviews = new ArrayList<>();
    private final String publishedDate;
    private final String bookImgUrl;
    private final List<String> authors;
    private final String description;
    private final List<String> categories;
    private final String bookMarked;
    private final Double averageRating;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    @Builder
    public BookDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, String bookMarked, Double averageRating, LocalDateTime createDate, LocalDateTime updateDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.publishedDate = publishedDate;
        this.bookImgUrl = bookImgUrl;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.bookMarked = bookMarked;
        this.averageRating = averageRating;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static BookDto toBookDto(Book book) {
        return BookDto.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .publishedDate(book.getPublishedDate())
                .bookImgUrl(book.getBookImgUrl())
                .authors(convertAuthors(book.getAuthors()))
                .description(book.getDescription())
                .categories(convertCategories(book.getCategories()))
                .bookMarked(book.getBookmarked())
                .averageRating(book.getAverageRating())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

    private static List<String> convertAuthors(String author) {
        String[] split = author.split(",");
        return List.of(split);
    }

    private static List<String> convertCategories(String category) {
        String[] split = category.split(",");
        return List.of(split);
    }
}
