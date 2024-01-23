package com.bookstore.readme.domain.collection.request.list;

import lombok.Getter;

/**
 * SearchTarget 가 중고(Used)인 경우 지정 <br>
 * Book <br>
 * Music <br>
 * DVD <br>
 */
@Getter
public enum SubSearchTarget {
    BOOK("Book"),
    MUSIC("Music"),
    DVD("DVD");

    private final String subSearchTarget;

    SubSearchTarget(String subSearchTarget) {
        this.subSearchTarget = subSearchTarget;
    }
}
