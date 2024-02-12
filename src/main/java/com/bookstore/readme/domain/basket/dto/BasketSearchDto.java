package com.bookstore.readme.domain.basket.dto;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class BasketSearchDto {
    private final Long basketId;
    private final String bookImgUrl;
    private final String bookTitle;
    private final Integer price;
    private final List<String> authors;

    @Builder
    public BasketSearchDto(Long basketId, String bookImgUrl, String bookTitle, Integer price, List<String> authors) {
        this.basketId = basketId;
        this.bookImgUrl = bookImgUrl;
        this.bookTitle = bookTitle;
        this.price = price;
        this.authors = authors;
    }

    public static BasketSearchDto of(Book book, Long basketId) {
        return BasketSearchDto.builder()
                .basketId(basketId)
                .bookImgUrl(book.getBookImgUrl())
                .bookTitle(book.getBookTitle())
                .price(book.getPrice())
                .authors(convertAuthors(book.getAuthors()))
                .build();
    }

    private static List<String> convertAuthors(String authors) {
        String[] split = authors.split(",");
        return Arrays.stream(split).toList();
    }
}
