package com.bookstore.readme.domain.member.dto;

import com.bookstore.readme.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MemberReviewsDto {

    private Long reviewId;
    private String content;
    private Double reviewRating;
    private Long bookId;
    private String bookTitle;
    private String[] authors;
    private String bookImgUrl;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static MemberReviewsDto of(Review review) {
        return MemberReviewsDto.builder()
                .reviewId(review.getId())
                .reviewRating(review.getReviewRating())
                .content(review.getContent())
                .bookId(review.getBook().getId())
                .bookTitle(review.getBook().getBookTitle())
                .authors(review.getBook().getAuthors().split(","))
                .bookImgUrl(review.getBook().getBookImgUrl())
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .build();
    }

    public static List<MemberReviewsDto> ofs(List<Review> reviews) {
        return reviews.stream()
                .map(MemberReviewsDto::of)
                .toList();
    }

}
