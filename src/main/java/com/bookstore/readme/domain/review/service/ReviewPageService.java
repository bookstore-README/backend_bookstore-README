package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.review.domain.Review;
import com.bookstore.readme.domain.review.dto.ReviewDto;
import com.bookstore.readme.domain.review.dto.ReviewPageDto;
import com.bookstore.readme.domain.review.dto.SortType;
import com.bookstore.readme.domain.review.repository.ReviewRepository;
import com.bookstore.readme.domain.review.request.ReviewPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewPageService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewPageDto searchPage(Long bookId, ReviewPageRequest request) {
        Sort.Direction direction = request.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        String property = request.getSortType().getSortType();
        Sort sort = Sort.by(direction, property);
        if (request.getSortType() != SortType.NEWEST)
            sort = sort.and(Sort.by(Sort.Order.desc(SortType.NEWEST.getSortType())));

        PageRequest pageRequest = PageRequest.of(request.getOffset(), request.getLimit(), sort);

        Page<Review> reviews = reviewRepository.findAllByBookId(bookId, pageRequest);
        List<Review> content = reviews.getContent();
        List<ReviewDto> list = content.stream()
                .map(ReviewDto::of)
                .toList();

        return ReviewPageDto.builder()
                .total(list.size())
                .totalPage(reviews.getTotalPages())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .hasNext(reviews.hasNext())
                .hasPreviews(reviews.hasPrevious())
                .reviews(list)
                .build();
    }

}
