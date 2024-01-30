package com.bookstore.readme.domain.collection.aladin.dto.subinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class SubInfoDto {

    //이 밑으로 상품 조회 API 에서만 제공
    private String subTitle;
    private String originalTitle;
    private Integer itemPage;
    private String previewImgList;
    private String cardReviewImgList;
    private String bestSellerRank;
    private String catno;
    private String specialFeature;
    private RatingInfoDto ratingInfo;
    private PackingDto packing;

    private List<AuthorDto> authors;
    private List<PaperBookListDto> paperBookList;
}
