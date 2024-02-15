package com.bookstore.readme.domain.order.dto;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.order.domain.OrderBook;
import lombok.Getter;

@Getter
public class OrderBookSaveDto {

    Integer bookId;

    Integer quantity;

    public OrderBook toEntity(Book book) {
        return OrderBook.builder()
                .book(book)
                .quantity(quantity)
                .build();
    }

}
