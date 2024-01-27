package com.bookstore.readme.domain.book.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BookDto {
    private final Long bookId;
    private final String bookTitle;
    private final String publishedDate;
    private final String bookImgUrl;
    private final List<String> authors;
    private final String description;
    private final List<String> categories;
    private final Integer bookMarked;
    private final Double averageRating;
    private final Integer price;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;
    private final List<ReviewDto> reviews = new ArrayList<>();

    @Builder
    public BookDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Integer bookMarked, Double averageRating, Integer price, LocalDateTime createDate, LocalDateTime updateDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.publishedDate = publishedDate;
        this.bookImgUrl = bookImgUrl;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.bookMarked = bookMarked;
        this.averageRating = averageRating;
        this.price = price;
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
                .price(book.getPrice())
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
