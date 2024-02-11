package com.bookstore.readme.domain.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ReviewRating {
    @Id
    @GeneratedValue
    @Column(name = "review_rating_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private Integer one;
    private Integer two;
    private Integer three;
    private Integer four;
    private Integer five;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public ReviewRating(Review review, Integer one, Integer two, Integer three, Integer four, Integer five) {
        this.review = review;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
    }

    public void changeReview(Review review) {
        this.review = review;
    }

    public void changeStar(ReviewRating rating) {
        one = rating.getOne();
        two = rating.getTwo();
        three = rating.getThree();
        four = rating.getFour();
        five = rating.getFive();
    }
}
