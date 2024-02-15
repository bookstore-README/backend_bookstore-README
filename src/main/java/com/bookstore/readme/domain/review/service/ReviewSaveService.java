package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.model.MemberDetails;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import com.bookstore.readme.domain.review.request.ReviewSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewSaveService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberDetails memberDetails, ReviewSaveRequest request) {
        Book book = bookRepository.getReferenceById(request.getBookId());
        Member member = memberRepository.getReferenceById(memberDetails.getMemberId());

        Review review = reviewRepository.findByMemberIdAndBookId(memberDetails.getMemberId(), request.getBookId())
                .orElseGet(() -> ReviewSaveRequest.toReview(request));

        if (review.getId() == null) {
            //Review Save
            review.changeBook(book);
            review.changeMember(member);
            book.addReviewCount();
        } else {
            //리뷰 새로운 값으로 변경
            Review newReview = ReviewSaveRequest.toReview(request);
            review.changeContent(newReview.getContent());
            review.changeReviewRating(newReview.getReviewRating());
        }


        //도서 별점 수정
        List<Review> reviews = book.getReviews();
        Double newRating = 0D;
        for (Review ratingReview : reviews) {
            Double reviewRating = ratingReview.getReviewRating();
            newRating += reviewRating;
        }

        newRating = reviews.isEmpty() ? 0D : newRating / reviews.size();
        book.changeRating(newRating);

        reviewRepository.save(review);
        return review.getId();
    }
}
