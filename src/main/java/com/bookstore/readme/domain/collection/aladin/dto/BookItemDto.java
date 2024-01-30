package com.bookstore.readme.domain.collection.aladin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookItemDto {
    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String description;
    private String itemId;
    private String isbn;
    private String isbn13;
    private Integer priceStandard;
    private String stockStatus;
    private String cover;
    private String publisher;
    private int categoryId;
    private String categoryName;
}
