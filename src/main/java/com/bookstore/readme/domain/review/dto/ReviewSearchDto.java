package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ReviewSearchDto extends ReviewDto {
    public static ReviewSearchDto of(Review review) {
        return ReviewSearchDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .reviewRating(review.getReviewRating())
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .build();
    }

    public static List<ReviewSearchDto> ofs(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewSearchDto::of)
                .toList();
    }
}
