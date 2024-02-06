package com.bookstore.readme.domain.book.dto;

import lombok.Getter;

@Getter
public enum SortType {
    STAR("averageRating"),
    REVIEW("reviewCount"),
    VIEW("viewCount"),
    POPULATION("viewCount"),
    PRICE("price"),
    NEW("publishedDate"),
    ID("id");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
