package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Getter;

import java.util.List;

@Getter
public class BookRequest {
    private String bookTitle;
    private String description;
    private List<String> authors;
    private List<String> categories;
    private Integer bookMarked;
    private Integer price;
    private Double averageRating;
    private String bookImgUrl;
    private String publishedDate;

    public Book toBook() {
        return Book.builder()
                .bookTitle(this.bookTitle)
                .publishedDate(this.publishedDate)
                .bookImgUrl(this.bookImgUrl)
                .authors(convertAuthor())
                .description(this.description)
                .categories(convertCategory())
                .bookmarked(this.bookMarked)
                .averageRating(this.averageRating)
                .build();
    }

    private String convertAuthor() {
        StringBuilder convert = new StringBuilder();
        for (String author : authors) {
            convert.append(author);
            convert.append(",");
        }

        convert.delete(convert.length() - 1, convert.length());
        return convert.toString();
    }

    private String convertCategory() {
        StringBuilder convert = new StringBuilder();
        for (String category : categories) {
            convert.append(category);
            convert.append(",");
        }

        convert.delete(convert.length() - 1, convert.length());
        return convert.toString();
    }
}
