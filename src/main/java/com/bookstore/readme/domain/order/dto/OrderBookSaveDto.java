package com.bookstore.readme.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderBookSaveDto {

    @Schema(description = "책 고유 ID")
    @NotNull(message = "책 고유 ID는 필수입니다.")
    Integer bookId;

    @Schema(description = "주문 책 권수")
    @NotNull(message = "주문한 책 권수는 필수입니다.")
    @Min(message = "주문한 책 권수는 1권 이상이여야 합니다.", value = 1)
    Integer quantity;

}
