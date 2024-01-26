package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.Review;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull(message = "책 아이디는 필수 입니다.")
    private Long bookId;

    @NotEmpty(message = "리뷰 제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "리뷰 글은 필수입니다.")
    @Size(max = 255, message = "리뷰의 최대 글자 수는 255자입니다.")
    private String content;

    public ReviewRequest(Long bookId, String title, String content) {
        this.bookId = bookId;
        this.title = title;
        this.content = content;
    }

    public Review toReview() {
        return Review.builder()
                .title(title)
                .content(content)
                .build();
    }
}
