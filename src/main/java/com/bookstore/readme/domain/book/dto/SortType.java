package com.bookstore.readme.domain.book.dto;

import lombok.Getter;

/**
 * STAR 별점순
 * REVIEW 리뷰순
 * VIEW 조회순
 * POPULATION 인기순
 * PRICE 가격순
 * ID 등록순
 */
@Getter
public enum SortType {
    STAR("averageRating"),
    REVIEW("reviewCount"),
    VIEW("viewCount"),
    POPULATION("bookmarkCount"),
    PRICE("price"),
    ID("id");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
