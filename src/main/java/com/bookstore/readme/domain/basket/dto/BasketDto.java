package com.bookstore.readme.domain.basket.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BasketDto {
    private final Long basketId;
    private final List<BasketSearchDto> baskets;

    public static BasketDto of(List<BasketSearchDto> baskets, Long basketId) {
        return BasketDto.builder()
                .basketId(basketId)
                .baskets(baskets)
                .build();
    }
}
