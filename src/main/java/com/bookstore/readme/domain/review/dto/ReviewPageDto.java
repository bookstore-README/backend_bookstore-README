package com.bookstore.readme.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewPageDto {
    private final Integer total;
    private final Integer totalPage;
    private final Integer offset;
    private final Integer limit;
    private final boolean hasNext;
    private final boolean hasPreviews;
    private final List<ReviewDto> reviews;

    @Builder
    public ReviewPageDto(Integer total, Integer totalPage, Integer offset, Integer limit, boolean hasNext, boolean hasPreviews, List<ReviewDto> reviews) {
        this.total = total;
        this.totalPage = totalPage;
        this.offset = offset;
        this.limit = limit;
        this.hasNext = hasNext;
        this.hasPreviews = hasPreviews;
        this.reviews = reviews;
    }
}
