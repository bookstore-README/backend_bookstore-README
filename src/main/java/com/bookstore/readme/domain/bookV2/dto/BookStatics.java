package com.bookstore.readme.domain.bookV2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookStatics {
    private final Integer bookmarkCount;
    private final Integer reviewCount;
    private final Integer viewCount;
    private final Integer quantityCount;
    private final Double averageRating;

    @Builder
    public BookStatics(Integer bookmarkCount, Integer reviewCount, Integer viewCount, Integer quantityCount, Double averageRating) {
        this.bookmarkCount = bookmarkCount;
        this.reviewCount = reviewCount;
        this.viewCount = viewCount;
        this.quantityCount = quantityCount;
        this.averageRating = averageRating;
    }
}
