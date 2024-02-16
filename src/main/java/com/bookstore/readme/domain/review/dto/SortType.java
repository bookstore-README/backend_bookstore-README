package com.bookstore.readme.domain.review.dto;

import lombok.Getter;

@Getter
public enum SortType {
    NEWEST("createDate"),
    STAR("reviewRating");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
