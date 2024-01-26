package com.bookstore.readme.domain.collection.aladin.dto.subinfo.review;

import lombok.Data;

@Data
public class ReviewListDto {
    private Integer reviewRank;
    private String writer;
    private String link;
    private String title;
}
