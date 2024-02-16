package com.bookstore.readme.domain.delivery.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.delivery.dto.DeliveryDto;
import com.bookstore.readme.domain.delivery.dto.DeliverySaveDto;
import com.bookstore.readme.domain.delivery.repository.DeliveryRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import com.bookstore.readme.domain.order.dto.OrderDto;
import com.bookstore.readme.domain.order.exception.OrderSaveException;
import com.bookstore.readme.domain.order.repository.OrderBookRepository;
import com.bookstore.readme.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional(rollbackFor = Exception.class)
    public DeliveryDto save(Long memberId, DeliverySaveDto deliverySaveDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        try {
            Delivery delivery = deliveryRepository.save(deliverySaveDto.toEntity(member));

            delivery.changeMember(member);

            Order order = orderRepository.save(Order.builder()
                    .delivery(delivery)
                    .orderBooks(new ArrayList<>())
                    .build());

            order.changeDelivery(delivery);

            for (OrderBookSaveDto orderSaveDto : deliverySaveDto.getOrderBooks()) {
                Book book = bookRepository.findById(orderSaveDto.getBookId().longValue())
                        .orElseThrow(() -> new NotFoundBookByIdException(orderSaveDto.getBookId().longValue()));

                OrderBook orderBook = OrderBook.builder()
                        .book(book)
                        .quantity(orderSaveDto.getQuantity())
                        .order(order)
                        .build();

                orderBook.changeOrder(order);

                orderBookRepository.save(orderBook);
            }

            OrderDto orderDto = OrderDto.of(order);

            return DeliveryDto.of(delivery, orderDto);
        } catch(Exception e) {
            throw new OrderSaveException();
        }
    }

}
