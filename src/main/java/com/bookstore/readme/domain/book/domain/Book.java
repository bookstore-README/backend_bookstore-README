package com.bookstore.readme.domain.book.domain;

import com.bookstore.readme.domain.category.domain.Category;
import com.bookstore.readme.domain.review.domain.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String bookTitle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    private String publishedDate;
    private String bookImgUrl;
    private String authors;
    private String description;
    private String categories;
    private String bookmarked;
    private Double averageRating;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Book(String bookTitle, String publishedDate, String bookImgUrl, String authors, String description, String categories, String bookmarked, Double averageRating) {
        this.bookTitle = bookTitle;
        this.publishedDate = publishedDate;
        this.bookImgUrl = bookImgUrl;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.bookmarked = bookmarked;
        this.averageRating = averageRating;
    }
}
