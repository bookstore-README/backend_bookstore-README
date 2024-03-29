package com.bookstore.readme.domain.review.repository;

import com.bookstore.readme.domain.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMemberIdAndBookId(Long memberId, Long bookId);

    List<Review> findAllByMemberId(Long memberId);

    Page<Review> findAllByBookId(Long bookId, Pageable pageable);
}
