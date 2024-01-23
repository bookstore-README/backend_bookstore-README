package com.bookstore.readme.domain.collection.dto.aladdin.subinfo.rating;

import lombok.Data;

@Data
public class RatingInfoDto {
    private Double ratingScore;
    private Integer ratingCount;
    private Integer commentReviewCount;
    private Integer myReviewCount;
}