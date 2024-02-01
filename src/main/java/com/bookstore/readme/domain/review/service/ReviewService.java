package com.bookstore.readme.domain.review.service;

import com.bookstore.readme.domain.review.request.ReviewRequest;
import com.bookstore.readme.domain.review.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse reviewList();
}
