package com.bookstore.readme.domain.order.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import com.bookstore.readme.domain.order.dto.OrderDto;
import com.bookstore.readme.domain.order.exception.NotFoundOrderByIdException;
import com.bookstore.readme.domain.order.exception.NotFoundOrderByMemberIdException;
import com.bookstore.readme.domain.order.exception.OrderSaveException;
import com.bookstore.readme.domain.order.repository.OrderBookRepository;
import com.bookstore.readme.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;
    private final OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public OrderDto save(Long memberId, List<OrderBookSaveDto> orderSaveDtos) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));
        try {
            Order order = orderRepository.save(Order.builder()
                    // .member(member)
                    .orderBooks(new ArrayList<>())
                    .build());

            for (OrderBookSaveDto orderSaveDto : orderSaveDtos) {
                Book book = bookRepository.findById(orderSaveDto.getBookId().longValue())
                        .orElseThrow(() -> new NotFoundBookByIdException(orderSaveDto.getBookId().longValue()));

                OrderBook orderBook = OrderBook.builder()
                        .book(book)
                        .count(orderSaveDto.getCount())
                        .order(order)
                        .build();

                orderBook.changeOrder(order);

                orderBookRepository.save(orderBook);
            }
            
            return OrderDto.of(order);
        } catch(Exception e) {
            throw new OrderSaveException();
        }
    }


    @Transactional
    public OrderDto findById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundOrderByIdException(orderId));
        
        return OrderDto.of(order);
    }

    @Transactional
    public List<OrderDto> findByMemberId(Long memberId) {
        // List<Order> orders = orderRepository.findByMemberId(memberId);

        // OrderDto.ofs(orders)

        return null;
    }

}
