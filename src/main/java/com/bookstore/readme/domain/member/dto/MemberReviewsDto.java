package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberReviewsDto {

    private Long reviewId;
    private String title;
    private String content;
    private Long bookId;
    private String bookTitle;
    private String authors;
    private String bookImgUrl;

    public static MemberReviewsDto of(Review review) {
        return MemberReviewsDto.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .bookId(review.getBook().getId())
                .bookTitle(review.getBook().getBookTitle())
                .authors(review.getBook().getAuthors())
                .bookImgUrl(review.getBook().getBookImgUrl())
                .build();
    }

    public static List<MemberReviewsDto> ofs(List<Review> reviews) {
        return reviews.stream()
                .map(MemberReviewsDto::of)
                .toList();
    }

}
