package com.bookstore.readme.domain.collection.request;


import lombok.Getter;

/**
 * 검색 대상 <br>
 * Book(기본값,도서) <br>
 * Foreign(외국도서) <br>
 * Music(음반) <br>
 * DVD(DVD) <br>
 * Used(중고샵(도서/음반/DVD 등) <br>
 * eBook(전자책) <br>
 * All(위의 모든 타겟) <br>
 */
@Getter
public enum SearchTarget {
    BOOK("Book"),
    FOREIGN("Foreign"),
    MUSIC("Music"),
    DVD("DVD"),
    USED("Used"),
    EBOOK("eBook"),
    ALL("All");
    
    private final String target;

    SearchTarget(String target) {
        this.target = target;
    }
}
