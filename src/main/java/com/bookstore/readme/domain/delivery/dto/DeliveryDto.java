package com.bookstore.readme.domain.delivery.dto;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.order.dto.OrderDto;
import com.bookstore.readme.domain.social.domain.SocialId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryStatus;
    private String name;
    private String phone;
    private String address;
    private String message;

    private String paymentMethod;
    private Integer paymentAmount;

    private Long memberId;
    private String nickname;
    private String email;
    private SocialId socialId;

    private OrderDto orderDto;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static DeliveryDto of(Delivery delivery) {
        OrderDto of = OrderDto.of(delivery.getOrder());

        return DeliveryDto.builder()
                .deliveryId(delivery.getId())
                .deliveryStatus(delivery.getDeliveryStatus().getStatus())
                .name(delivery.getName())
                .phone(delivery.getPhone())
                .address(delivery.getAddress())
                .message(delivery.getMessage())
                .paymentMethod(delivery.getPaymentMethod())
                .paymentAmount(delivery.getPaymentAmount())
                .memberId(delivery.getMember().getId())
                .nickname(delivery.getMember().getNickname())
                .email(delivery.getMember().getEmail())
                .socialId(delivery.getMember().getSocialId())
                .orderDto(of)
                .createDate(delivery.getCreateDate())
                .updateDate(delivery.getUpdateDate())
                .build();
    }

    public static DeliveryDto of(Delivery delivery, OrderDto orderDto) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getId())
                .deliveryStatus(delivery.getDeliveryStatus().getStatus())
                .name(delivery.getName())
                .phone(delivery.getPhone())
                .address(delivery.getAddress())
                .message(delivery.getMessage())
                .paymentMethod(delivery.getPaymentMethod())
                .paymentAmount(delivery.getPaymentAmount())
                .memberId(delivery.getMember().getId())
                .nickname(delivery.getMember().getNickname())
                .email(delivery.getMember().getEmail())
                .socialId(delivery.getMember().getSocialId())
                .orderDto(orderDto)
                .createDate(delivery.getCreateDate())
                .updateDate(delivery.getUpdateDate())
                .build();
    }

    public static List<DeliveryDto> ofs(List<Delivery> deliveries) {
        return deliveries.stream()
                .map(DeliveryDto::of)
                .toList();
    }
}
