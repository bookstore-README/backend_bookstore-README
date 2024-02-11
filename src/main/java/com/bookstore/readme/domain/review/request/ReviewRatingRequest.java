package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.ReviewRating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRatingRequest {
    @NotNull(message = "별점은 필수 값입니다.")
    private final Integer one;

    @NotNull(message = "별점은 필수 값입니다.")
    private final Integer two;

    @NotNull(message = "별점은 필수 값입니다.")
    private final Integer three;

    @NotNull(message = "별점은 필수 값입니다.")
    private final Integer four;

    @NotNull(message = "별점은 필수 값입니다.")
    private final Integer five;

    public ReviewRatingRequest(Integer one, Integer two, Integer three, Integer four, Integer five) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
    }

    public ReviewRating toRating() {
        return ReviewRating.builder()
                .one(one)
                .two(two)
                .three(three)
                .four(four)
                .five(five)
                .build();
    }
}
