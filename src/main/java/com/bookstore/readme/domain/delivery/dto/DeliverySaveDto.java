package com.bookstore.readme.domain.delivery.dto;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DeliverySaveDto {

    private String name;
    private String phone;
    private String address;
    private String message;
    private String paymentMethod;
    private Integer paymentAmount;
    private List<OrderBookSaveDto> orderBooks;

    public Delivery toEntity(Member member) {
        return Delivery.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .message(message)
                .deliveryStatus(DeliveryStatus.READY)
                .paymentMethod(paymentMethod)
                .paymentAmount(paymentAmount)
                .build();
    }

}
