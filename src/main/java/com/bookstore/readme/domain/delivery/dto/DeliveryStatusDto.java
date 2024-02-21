package com.bookstore.readme.domain.delivery.dto;

import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeliveryStatusDto {

    @NotEmpty(message = "필수 주문 아이디입니다.")
    private Integer deliveryId;

    @NotEmpty(message = "필수 주문 상태값입니다.")
    private DeliveryStatus deliveryStatus;

}
