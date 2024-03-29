package com.bookstore.readme.domain.book.domain;

import com.bookstore.readme.domain.bookmark.domain.Bookmark;
import com.bookstore.readme.domain.order.domain.OrderBook;
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
    @Column(name = "book_id")
    private Long id;
    @Column(length = 1000)
    private String bookTitle;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String authors;
    private String categories;
    private Integer price;
    private Double averageRating;
    private String ratingDist;
    private String bookImgUrl;
    private Integer reviewCount;
    private Integer bookmarkCount;
    private Integer viewCount;
    private Integer quantityCount;

    private String publisher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Column(name = "published_date", columnDefinition = "DATETIME(6)")
    private LocalDateTime publishedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updateDate;

    @Builder
    public Book(String bookTitle, Integer quantityCount, LocalDateTime publishedDate, String bookImgUrl, String authors, String description, String categories, Double averageRating, Integer price, Integer reviewCount, Integer bookmarkCount, Integer viewCount, String publisher) {
        this.bookTitle = bookTitle;
        this.publishedDate = publishedDate;
        this.bookImgUrl = bookImgUrl;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.averageRating = averageRating;
        this.price = price;
        this.reviewCount = reviewCount;
        this.bookmarkCount = bookmarkCount;
        this.viewCount = viewCount;
        this.publisher = publisher;
        this.quantityCount = quantityCount;
    }

    public void changeBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void changeRating(Double rating) {
        this.averageRating = rating;
    }

    public void subReviewCount() {
        this.reviewCount -= 1;
    }

    public void addReviewCount() {
        this.reviewCount += 1;
    }
}
