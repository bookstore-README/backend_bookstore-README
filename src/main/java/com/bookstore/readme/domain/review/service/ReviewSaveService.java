package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
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

    @Transactional
    public Long save(ReviewSaveRequest request) {
        Long bookId = request.getBookId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundBookByIdException(bookId));

        Review review = ReviewRequest.toReview(request);
        review.changeBook(book);
        reviewRepository.save(review);
        
        return review.getId();
    }
}
