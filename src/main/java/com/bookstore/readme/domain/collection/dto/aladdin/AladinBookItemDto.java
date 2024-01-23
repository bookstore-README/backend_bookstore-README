package com.bookstore.readme.domain.collection.dto.aladdin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"subInfo"})
public class AladinBookItemDto {

    private String itemId;
    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private int priceSales;
    private int priceStandard;
    private String mallType;
    private String stockStatus;
    private int mileage;
    private String cover;
    private int categoryId;
    private String categoryName;
    private String publisher;
    private int salesPoint;
    private boolean adult;
    private boolean fixedPrice;
    private int customerReviewRank;
    private String creator;

    @JsonProperty(value = "seriesInfo")
    private AladinSeriesInfoDto seriesInfo;

}
