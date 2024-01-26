package com.bookstore.readme.domain.collection.request;

import lombok.Getter;

/**
 * 표지 크기 <br>
 * Big 큰 크기, 너비 200px<br>
 * MidBig 중간 큰 크기, 너비 150px<br>
 * Mid 기본값, 너비 85px<br>
 * Small 작은 크기, 너비 75px<br>
 * Mini 매우 작은 크기, 너비 65px<br>
 * None 없음
 */
@Getter
public enum CoverSize {
    BIG("Big"),
    MIDBIG("MidBig"),
    MID("Mid"),
    SMALL("Small"),
    MINI("Mini"),
    NONE("None");

    private final String coverSize;

    CoverSize(String coverSize) {
        this.coverSize = coverSize;
    }
}
