package com.bookstore.readme.domain.collection.dto.aladdin;

import com.bookstore.readme.domain.collection.dto.aladdin.subinfo.SubInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class BookItemDto {

    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String isbn13;
    private Integer priceSales;
    private Integer priceStandard;
    private String mallType;
    private String stockStatus;
    private Integer mileage;
    private String cover;
    private String publisher;
    private Integer salesPoint;
    private boolean adult;
    private boolean fixedPrice;
    private int customerReviewRank;

    //베스트셀러인 경우만 노출, 베스트셀러 순위 관련 추가 정보
    private String bestDuration;
    //베스트셀러인 경우만 노출, 베스트셀러 순위 정보
    private Integer bestRank;

    @JsonProperty(value = "seriesInfo")
    private SeriesInfoDto seriesInfo;

    @JsonProperty(value = "subInfo")
    private SubInfoDto subInfo;

    private String itemId;
    private int categoryId;
    private String categoryName;

    //상품 조회 API 에서만 제공
    //추가 요청 시 제공
    private String fullDescription;
    private String fullDescription2;
    private List<CategoryIdListDto> categoryIdList;
}
