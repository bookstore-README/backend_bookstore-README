package com.bookstore.readme.domain.basket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class BasketDeleteRequest {
    @NotEmpty(message = "장바구니 아이디는 필수 입력입니다.")
    @Schema(description = "삭제할 장바구니 아이디", example = "1,2,3")
    private final List<Integer> basketIds;

    public BasketDeleteRequest(List<Integer> basketIds) {
        this.basketIds = basketIds;
    }
}
