package com.bookstore.readme.domain.review.domain;

import com.bookstore.readme.domain.book.domain.Book;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @CreatedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Review(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeBook(Book book) {
        this.book = book;
        book.getReviews().add(this);
    }
}
