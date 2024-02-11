package com.bookstore.readme.domain.review.domain;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String title;
    private String content;
    private Double reviewRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Review(String title, String content, Double reviewRating) {
        this.title = title;
        this.content = content;
        this.reviewRating = reviewRating;
    }

    public void changeBook(Book book) {
        this.book = book;
        book.getReviews().add(this);
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }
}
