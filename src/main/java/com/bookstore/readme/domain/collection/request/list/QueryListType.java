package com.bookstore.readme.domain.collection.request.list;

import lombok.Getter;

/**
 * 검색어 종류 <br>
 * ItemNewAll(신간 전체) <br>
 * ItemNewSpecial(주목할 만한 신간 리스트) <br>
 * ItemEditorChoice(편집차 추천 리스트, 카테고리로만 조회 가능 - 국내도서/음반/외서만 지원) <br>
 * Bestseller(베스트 셀러) <br>
 * BlogBest(블로거 베스트셀러, 국내도서만 조회 가능) <br>
 */
@Getter
public enum QueryListType {
    ITEMNEWALL("ItemNewAll"),
    ITEMNEWSPECIAL("ItemNewSpecial"),
    ITEMEDITORCHOICE("ItemEditorChoice"),
    BESTSELLER("Bestseller"),
    BLOGBEST("BlogBest");

    private final String queryType;

    QueryListType(String queryType) {
        this.queryType = queryType;
    }
}
