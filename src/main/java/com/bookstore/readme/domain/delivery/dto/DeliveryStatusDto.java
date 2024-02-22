package com.bookstore.readme.domain.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeliveryStatusDto {

    @NotNull(message = "필수 배송 아이디입니다.")
    @Schema(name = "배송 아이디", example = "1")
    private Integer deliveryId;

    @NotNull(message = "필수 배송 상태값입니다.")
    @Schema(name = "배송 상태값", example = "READY, DELIVERING, COMPLETE, EXCHANGE_AND_REFUND, CONFIRM, CANCEL")
    private String deliveryStatus;

}
