package com.bookstore.readme.domain.review.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewUpdateRequest extends ReviewRequest {
    @NotNull(message = "리뷰 평점은 필수입니다.")
    @Schema(example = "5", description = "수정할 평점")
    private final Double rating;
}
