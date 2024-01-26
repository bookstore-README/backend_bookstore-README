package com.bookstore.readme.domain.collection.aladin.request.search;

import lombok.Getter;

/**
 * 검색어 종류 <br>
 * Keyword 기본값, 제목+저자<br>
 * Title 제목검색<br>
 * Author 저자검색<br>
 * Publisher 출판사검색<br>
 */
@Getter
public enum QueryType {
    KEYWORD("Keyword"),
    TITLE("Title"),
    AUTHOR("Author"),
    PUBLISHER("Publisher");

    private final String type;

    QueryType(String type) {
        this.type = type;
    }
}
