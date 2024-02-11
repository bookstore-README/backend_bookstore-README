package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewListDto;
import com.bookstore.readme.domain.review.dto.ReviewSearchDto;
import com.bookstore.readme.domain.review.exception.NotFoundReviewByIdException;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewSearchService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewSearchDto searchReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewByIdException(reviewId));

        return ReviewSearchDto.of(review);
    }

    @Transactional
    public ReviewListDto searchReviewByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        List<Review> reviews = member.getReviews();
        return ReviewListDto.builder()
                .memberId(memberId)
                .reviews(ReviewSearchDto.ofs(reviews))
                .build();
    }
}
