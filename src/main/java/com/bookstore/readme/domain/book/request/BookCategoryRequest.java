package com.bookstore.readme.domain.book.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BookCategoryRequest extends BookPageRequest {
    private final List<Integer> categories;

    public BookCategoryRequest(Integer bookId, Integer limit, List<String> sort, Boolean ascending, String search, List<Integer> categories) {
        super(bookId, limit, sort, ascending, search);
        this.categories = categories;
    }
}
