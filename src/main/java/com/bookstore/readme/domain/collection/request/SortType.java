package com.bookstore.readme.domain.collection.request;

import lombok.Getter;

/**
 * 정렬순서 <br>
 * Accuracy 기본값, 관련도 <br>
 * PublishTime 출간일 <br>
 * Title 제목 <br>
 * SalesPoint 판매량 <br>
 * CustomerRating 고객평점 <br>
 * MyReviewCount 마이리뷰갯수 <br>
 */
@Getter
public enum SortType {
    ACCURACY("Accuracy"),
    PUBLISHTIME("PublishTime"),
    TITLE("Title"),
    SALESPOINT("SalesPoint"),
    CUSTOMERRATING("CustomerRating"),
    MYREVIEWCOUNT("MyReviewCount");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }
}
