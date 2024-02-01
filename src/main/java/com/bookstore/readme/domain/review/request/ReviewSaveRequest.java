package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReviewSaveRequest extends ReviewRequest {
    public ReviewSaveRequest(Long bookId, String title, String content) {
        super(bookId, title, content);
    }

    public static Review toReview(ReviewRequest request) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
