package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.review.domain.Review;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ReviewSearchDto extends ReviewDto {
    private String userNickname;
    private String profileImg;

    public static ReviewSearchDto of(Review review) {
        return ReviewSearchDto.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .reviewRating(review.getReviewRating())
                .userNickname(review.getMember().getNickname())
                .profileImg(review.getMember().getProfileImage())
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
