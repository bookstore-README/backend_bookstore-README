package com.bookstore.readme.domain.book.dto.favorite;

import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.category.dto.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BookDto {
    private final Long bookId;
    private final String bookTitle;
    private final String bookImgUrl;
    private final Integer price;
    private final List<String> authors;
    private final List<String> categories;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    @Builder
    public BookDto(Long bookId, String bookTitle, String bookImgUrl, Integer price, List<String> authors, List<String> categories, LocalDateTime createDate, LocalDateTime updateDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImgUrl = bookImgUrl;
        this.price = price;
        this.authors = authors;
        this.categories = categories;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    public static BookDto of(Book book) {
        return BookDto.builder()
                .bookId(book.getId())
                .bookTitle(book.getBookTitle())
                .bookImgUrl(book.getBookImgUrl())
                .price(book.getPrice())
                .authors(convertAuthors(book.getAuthors()))
                .categories(convertCategories(book.getCategories()))
                .createDate(book.getCreateDate())
                .updateDate(book.getUpdateDate())
                .build();
    }

    protected static List<String> convertAuthors(String author) {
        String[] split = author.split(",");
        return List.of(split);
    }

    protected static List<String> convertCategories(String category) {
        String[] split = category.split(",");
        return List.of(split);
    }
}
