package com.bookstore.readme.domain.order.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import com.bookstore.readme.domain.order.dto.OrderDto;
import com.bookstore.readme.domain.order.repository.OrderBookRepository;
import com.bookstore.readme.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBookService {

    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;

    @Transactional
    public void save(OrderDto orderDto, List<OrderBookSaveDto> orderSaveDtos) {

        Order order = orderDto.toEntity();

        for (OrderBookSaveDto orderSaveDto : orderSaveDtos) {
            Book book = bookRepository.findById(orderSaveDto.getBookId().longValue())
                    .orElseThrow(() -> new NotFoundBookByIdException(orderSaveDto.getBookId().longValue()));

            OrderBook orderBook = OrderBook.builder()
                    .book(book)
                    .quantity(orderSaveDto.getQuantity())
                    .order(order)
                    .build();

            // orderBook.changeOrder(order);

            orderBookRepository.save(orderBook);
        }

    }
}
