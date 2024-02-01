package com.bookstore.readme.domain.book.dto.search;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
public class BookSearchDto extends BookDto {
    public BookSearchDto(Long bookId, String bookTitle, String publishedDate, String bookImgUrl, List<String> authors, String description, List<String> categories, Integer bookMarked, Double averageRating, Integer price, LocalDateTime createDate, LocalDateTime updateDate) {
        super(bookId, bookTitle, publishedDate, bookImgUrl, authors, description, categories, bookMarked, averageRating, price, createDate, updateDate);
    }

    public static BookSearchDto of(Book book) {
        return BookSearchDto.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .publishedDate(book.getPublishedDate())
                .bookImgUrl(book.getBookImgUrl())
                .authors(convertAuthors(book.getAuthors()))
                .description(book.getDescription())
                .categories(convertCategories(book.getCategories()))
                .bookMarked(book.getBookmarked())
                .averageRating(book.getAverageRating())
                .price(book.getPrice())
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

}
