package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.domain.ReviewRating;
import com.bookstore.readme.domain.review.repository.ReviewRatingRepository;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import com.bookstore.readme.domain.review.request.ReviewRatingRequest;
import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.request.ReviewSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewSaveService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final ReviewRatingRepository reviewRatingRepository;

    @Transactional
    public Long save(ReviewSaveRequest request) {
        Book book = bookRepository.getReferenceById(request.getBookId());
        Member member = memberRepository.getReferenceById(request.getMemberId());

        Review review = reviewRepository.findByMemberIdAndBookId(request.getMemberId(), request.getBookId())
                .orElseGet(() -> ReviewSaveRequest.toReview(request));

        if (review.getId() == null) {
            //새로운 리뷰
            ReviewRating rating = request.getReviewRating().toRating();
            rating.changeReview(review);

            //Review Save
            review.changeBook(book);
            review.changeMember(member);
            review.changeReviewRating(rating);

            reviewRepository.save(review);
            reviewRatingRepository.save(rating);
        } else {

            //리뷰 새로운 값으로 변경
            Review newReview = ReviewSaveRequest.toReview(request);
            review.changeTitle(newReview.getTitle());
            review.changeContent(newReview.getContent());

            //레이팅 새로운 값으로 변경
            ReviewRating reviewRating = review.getReviewRating();
            ReviewRating newRating = request.getReviewRating().toRating();
            reviewRating.changeStar(newRating);

            reviewRepository.save(review);
            reviewRatingRepository.save(reviewRating);
        }

        return review.getId();
    }
}
