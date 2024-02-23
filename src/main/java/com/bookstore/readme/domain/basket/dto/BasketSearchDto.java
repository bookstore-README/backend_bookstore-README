package com.bookstore.readme.domain.basket.dto;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class BasketSearchDto {
    private final Long basketId;
    private final Long bookId;
    private final String bookImgUrl;
    private final String bookTitle;
    private final Integer price;
    private final Integer count;
    private final List<String> authors;

    @Builder
    public BasketSearchDto(Long basketId, Long bookId, String bookImgUrl, String bookTitle, Integer price, Integer count, List<String> authors) {
        this.basketId = basketId;
        this.bookId = bookId;
        this.bookImgUrl = bookImgUrl;
        this.bookTitle = bookTitle;
        this.price = price;
        this.count = count;
        this.authors = authors;
    }

    public static BasketSearchDto of(Book book, Long basketId, Integer count) {
        return BasketSearchDto.builder()
                .basketId(basketId)
                .bookId(book.getId())
                .bookImgUrl(book.getBookImgUrl())
                .bookTitle(book.getBookTitle())
                .price(book.getPrice())
                .count(count)
                .authors(convertAuthors(book.getAuthors()))
                .build();
    }

    private static List<String> convertAuthors(String authors) {
        String[] split = authors.split(",");
        return Arrays.stream(split).toList();
    }
}
