package com.bookstore.readme.domain.collection.aladin.request.list;

import com.bookstore.readme.domain.collection.aladin.request.CoverSize;
import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AladdinListRequest {
    @NotNull(message = "queryType 필수 입력 값입니다.")
    private QueryListType queryType;

    private SearchTarget searchTarget;

    private SubSearchTarget subSearchTarget;

    //검색결과 시작 페이지 - 1이상, 양의 정수(기본값:1)
    @Min(1)
    private Integer start;

    //검색결과 한 페이지당 최대 출력 개수 - 1이상 100이하, 양의 정수(기본 10)
    @Min(1)
    @Max(100)
    private Integer maxResults;

    private CoverSize cover;

    //특정 분야로 검색결과 제한.
    //private String categoryId;

    //출력방법(JSON)
    //private String outPut;

    //검색어의 인코딩 값을 설정 (utf-8, euc-kr)
    //private String inputEncoding;

    //품절, 절판 등 재고 없는 상품 필터링(1인 경우 제외)
    @Min(0)
    @Max(1)
    private Integer outofStockFilter;

    /**
     * 베스트셀러를 조회할 주간( 기본값 0 )<br>
     * ex) Year=2022&Month=5&Week=3
     */
    private Integer year;
    private Integer month;
    private Integer week;
}
