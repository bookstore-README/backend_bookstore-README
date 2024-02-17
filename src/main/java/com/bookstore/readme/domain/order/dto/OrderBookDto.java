package com.bookstore.readme.domain.order.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.order.domain.OrderBook;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class OrderBookDto {

    private Long orderBookId;

    private Long bookId;

    private String bookTitle;

    private String authors;

    private Integer price;

    private Integer quantity;

    public static OrderBookDto of(OrderBook orderBook) {
        return OrderBookDto.builder()
                .orderBookId(orderBook.getId())
                .bookId(orderBook.getBook().getId())
                .bookTitle(orderBook.getBook().getBookTitle())
                .authors(orderBook.getBook().getAuthors())
                .price(orderBook.getBook().getPrice())
                .quantity(orderBook.getQuantity())
                .build();
    }

    public static List<OrderBookDto> ofs(List<OrderBook> orderBooks) {
        return orderBooks.stream()
                .map(OrderBookDto::of)
                .toList();
    }

}