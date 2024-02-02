package com.bookstore.readme.domain.review.dto;

import com.bookstore.readme.domain.notice.domain.Notice;
import com.bookstore.readme.domain.notice.dto.NoticeSearchDto;
import com.bookstore.readme.domain.review.domain.Review;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class ReviewSearchDto extends ReviewDto {
    public ReviewSearchDto(Long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        super(id, title, content, createDate, updateDate);
    }

    public static ReviewSearchDto of(Review review) {
        return ReviewSearchDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
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
