package com.bookstore.readme.domain.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private Long id;

    //평균 평점
    private Double ratingScore;
    private Integer ratingCount;
    //100자평 전체 리뷰 수
    private Integer commentReviewCount;
    //마이리뷰
    private Integer myReviewCount;
}
