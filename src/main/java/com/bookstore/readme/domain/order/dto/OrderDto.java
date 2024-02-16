package com.bookstore.readme.domain.order.dto;

import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import com.bookstore.readme.domain.social.domain.SocialId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class OrderDto {

    private Long orderId;

    private Long memberId;

    private String nickname;

    private String email;

    private SocialId socialId;

    private List<OrderBookDto> orderBook;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static OrderDto of(Order order) {
        List<OrderBookDto> ofs = OrderBookDto.ofs(order.getOrderBooks());

        return OrderDto.builder()
                .orderId(order.getId())
                .memberId(order.getMember().getId())
                .nickname(order.getMember().getNickname())
                .email(order.getMember().getEmail())
                .socialId(order.getMember().getSocialId())
                .orderBook(ofs)
                .createTime(order.getCreateDate())
                .updateTime(order.getUpdateDate())
                .build();
    }

    public static List<OrderDto> ofs(List<Order> orders) {
        return orders.stream()
                .map(OrderDto::of)
                .toList();
    }

}
