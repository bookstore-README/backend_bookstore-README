package com.bookstore.readme.domain.basket.dto;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BasketSearchDto {
    private final String bookImgUrl;
    private final String bookTitle;
    private final Integer price;
    private final List<String> authors;

    @Builder
    public BasketSearchDto(String bookImgUrl, String bookTitle, Integer price, List<String> authors) {
        this.bookImgUrl = bookImgUrl;
        this.bookTitle = bookTitle;
        this.price = price;
        this.authors = authors;
    }

    public static BasketSearchDto of(Book book) {
        return BasketSearchDto.builder()
                .bookImgUrl(book.getBookImgUrl())
                .bookTitle(book.getBookTitle())
                .price(book.getPrice())
                .build();
    }
}
