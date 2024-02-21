package com.bookstore.readme.domain.bookV2.request;

import lombok.Getter;

@Getter
public enum SortType {
    STAR("averageRating"),
    REVIEW("reviewCount"),
    VIEW("viewCount"),
    //    POPULATION("viewCount"),
    PRICE("price"),
    NEWEST("publishedDate"),
    BESTSELLER("quantityCount");

    private final String type;

    SortType(String type) {
        this.type = type;
    }
}
