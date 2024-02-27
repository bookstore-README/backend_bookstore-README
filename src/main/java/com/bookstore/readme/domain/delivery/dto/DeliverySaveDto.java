package com.bookstore.readme.domain.delivery.dto;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class DeliverySaveDto {

    @Schema(description = "배송 받는 사람 이름", example = "아무개")
    @NotNull(message = "배송 받는 사람의 이름은 필수입니다.")
    private String name;

    @Schema(description = "배송 받는 사람 전화번호", example = "010-0000-0000")
    @NotNull(message = "배송 받는 사람의 이름은 필수입니다.")
    private String phone;

    @Schema(description = "배송 받는 사람 주소", example = "주소 풀네임")
    @NotNull(message = "배송 받는 사람의 주소는 필수입니다.")
    private String address;

    @Schema(description = "배송 시 전달할 메시지")
    private String message;

    @Schema(description = "기본 배송지 저장 여부")
    private Boolean basicAddress;

    @Schema(description = "결제 방법", example = "CARD")
    @NotNull(message = "결제 방법은 필수입니다.")
    private String paymentMethod;

    @Schema(description = "결제 금액", example = "42000")
    @NotNull(message = "결제 금액은 필수입니다.")
    private Integer paymentAmount;

    @Schema(description = "장바구니 아이디 리스트")
    private List<Integer> basketIds;

    @Schema(description = "주문한 책 목록")
    @NotNull(message = "주문한 책 목록은 필수입니다.")
    private List<OrderBookSaveDto> orderBooks;

    public Delivery toEntity(Order order) {
        return Delivery.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .message(message)
                .order(order)
                .deliveryStatus(DeliveryStatus.READY)
                .paymentMethod(paymentMethod)
                .paymentAmount(paymentAmount)
                .build();
    }

}
