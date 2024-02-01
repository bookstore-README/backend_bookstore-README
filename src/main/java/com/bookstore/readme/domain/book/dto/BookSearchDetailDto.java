package com.bookstore.readme.domain.book.dto;

import com.bookstore.readme.domain.review.dto.ReviewDto;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
public class BookSearchDetailDto extends BookDto {
    private List<ReviewDto> reviews;

    public BookSearchDetailDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Integer bookMarked, Double averageRating, Integer price, LocalDateTime createDate, LocalDateTime updateDate, List<ReviewDto> reviews) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, bookMarked, averageRating, price, createDate, updateDate);
        this.reviews = reviews;
    }
}
