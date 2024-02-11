package com.bookstore.readme.domain.bookmark.dto;

import lombok.Getter;

@Getter
public enum SortType {
    PRICE("book.price"),
    ID("id");
    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
