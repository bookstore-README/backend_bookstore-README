package com.bookstore.readme.domain.book.dto.page;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookDto {
    private final Long bookId;
    private final String bookTitle;
    private final LocalDateTime publishedDate;
    private final String bookImgUrl;
    private final List<String> authors;
    private final String description;
    private final List<String> categories;
    private final Double averageRating;
    private final Integer price;
    private final Integer bookmarkCount;
    private final Integer reviewCount;
    private final Integer viewCount;
    private final String publisher;
    private final Integer quantityCount;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    public BookDto(Long bookId, String bookTitle, LocalDateTime publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Double averageRating, Integer price, Integer bookmarkCount, Integer reviewCount, Integer viewCount, String publisher, Integer quantityCount, LocalDateTime createDate, LocalDateTime updateDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.publishedDate = publishedDate;
        this.bookImgUrl = bookImgUrl;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.averageRating = averageRating;
        this.price = price;
        this.bookmarkCount = bookmarkCount;
        this.reviewCount = reviewCount;
        this.viewCount = viewCount;
        this.publisher = publisher;
        this.quantityCount = quantityCount;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static BookDto of(Book book) {
        return BookDto.builder()
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
                .quantityCount(book.getQuantityCount())
                .publisher(book.getPublisher())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

    protected static List<String> convertAuthors(String author) {
        String[] split = author.split(",");
        return List.of(split);
    }

    protected static List<String> convertCategories(String category) {
        String[] split = category.split(",");
        return List.of(split);
    }
}
