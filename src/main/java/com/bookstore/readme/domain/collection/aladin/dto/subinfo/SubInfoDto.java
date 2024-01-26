package com.bookstore.readme.domain.collection.aladin.dto.subinfo;

import com.bookstore.readme.domain.collection.aladin.dto.subinfo.author.AuthorDto;
import com.bookstore.readme.domain.collection.aladin.dto.subinfo.ebook.EbookListDto;
import com.bookstore.readme.domain.collection.aladin.dto.subinfo.packing.PackingDto;
import com.bookstore.readme.domain.collection.aladin.dto.subinfo.newbook.NewBookListDto;
import com.bookstore.readme.domain.collection.aladin.dto.subinfo.rating.RatingInfoDto;
import com.bookstore.readme.domain.collection.aladin.dto.subinfo.used.UsedListDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class SubInfoDto {
    private List<EbookListDto> ebookList;
    private UsedListDto usedList;
    private String usedType;
    private List<NewBookListDto> newBookList;
    private List<PaperBookListDto> paperBookList;

    //이 밑으로 상품 조회 API 에서만 제공
    private String subTitle;
    private String originalTitle;
    private Integer itemPage;
    private String previewImgList;
    private String cardReviewImgList;
    private RatingInfoDto ratingInfo;
    private String bestSellerRank;
    private List<AuthorDto> authors;
    private Integer c2bsales;
    private Object c2bsales_price;
    private Boolean b2bSupply;
    private String catno;
    private String recommendationComment;
    private String specialFeature;
    private Object subBarcode;
    private PackingDto packing;

//    추가 정보 제공
//    private String toc;
//    private String story;
//    private Integer disc;
//    private String playTime;
//    private String language;
//    private String caption;
//    private String screenrate;
//    private String recordingtype;
//    private String areacode;
//    private List<EventListDto> eventList;
//    private List<ReviewListDto> reviewList;
//    private List<PhraseListDto> phraseList;
//    private List<MdRecommendList> mdRecommendList;
}
