package com.bookstore.readme.domain.order.dto;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class OrderDto {

    private Long orderId;

    private Member member;

    private List<OrderBook> orderBooks;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Order toEntity() {
        return Order.builder()
                .id(orderId)
                .member(member)
                .orderBooks(orderBooks)
                .build();
    }

    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .member(order.getMember())
                .orderBooks(order.getOrderBooks())
                .createTime(order.getCreateDate())
                .updateTime(order.getUpdateDate())
                .build();
    }

}
