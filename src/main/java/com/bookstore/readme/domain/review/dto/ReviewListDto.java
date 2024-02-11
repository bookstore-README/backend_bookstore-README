package com.bookstore.readme.domain.review.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewListDto {
    private final Long memberId;
    private final List<ReviewSearchDto> reviews;

    @Builder
    public ReviewListDto(Long memberId, List<ReviewSearchDto> reviews) {
        this.memberId = memberId;
        this.reviews = reviews;
    }
}
