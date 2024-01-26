package com.bookstore.readme.domain.review.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReviewListDto {
    private Integer total;
    private Integer limit;
    private Integer page;
    private List<ReviewDto> reviews;

    @Builder
    public ReviewListDto(Integer total, Integer limit, Integer page, List<ReviewDto> reviews) {
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.reviews = reviews;
    }
}
