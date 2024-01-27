package com.bookstore.readme.domain.book.dto;

import lombok.Getter;

@Getter
public enum SortType {
    POPULATION("bookmarked,id"),
    PRICE("price,id"),
    ID("id");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
