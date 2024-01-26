package com.bookstore.readme.domain.book.request;

import com.bookstore.readme.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookRequest {
    private String bookTitle;
    private Integer views;
    private String publishedDate;
    private String bookImgUrl;
    private List<String> authors;
    private String description;
    private List<String> categories;
    private String bookMarked;
    private Double averageRating;

    public Book toBook() {
        return Book.builder()
                .bookTitle(this.bookTitle)
                .views(this.views)
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
