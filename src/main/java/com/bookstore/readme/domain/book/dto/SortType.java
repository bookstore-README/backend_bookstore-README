package com.bookstore.readme.domain.book.dto;

import lombok.Getter;

@Getter
public enum SortType {
    POPULATION("bookmark_count"),
    PRICE("price"),
    ID("id");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
