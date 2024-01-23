package com.bookstore.readme.domain.collection.request.search;

import com.bookstore.readme.domain.collection.request.CoverSize;
import com.bookstore.readme.domain.collection.request.SearchTarget;
import com.bookstore.readme.domain.collection.request.SortType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AladdinSearchRequest {

    //검색어
    @NotEmpty(message = "검색어는 필수로 입력해야 합니다.")
    private String query;

    //검색어 종류 (Keyword, Title, Author, Publisher)
    private QueryType queryType;

    private SearchTarget searchTarget;

    //검색결과 시작 페이지 - 1이상, 양의 정수(기본값:1)
    @Min(value = 1, message = "최소값은 1입니다.")
    private Integer start;

    //검색결과 한 페이지당 최대 출력 개수 - 1이상 100이하, 양의 정수(기본 10)
    @Min(value = 1, message = "최소값은 1 입니다.")
    @Max(value = 100, message = "최대값은 100 입니다.")
    private Integer maxResults;

    private SortType sort;

    private CoverSize cover;

    //품절, 절판 등 재고 없는 상품 필터링(1인 경우 제외)
    @Min(0)
    @Max(1)
    private Integer outofStockFilter;

    //특정 분야로 검색결과 제한.
    //private String categoryId;

    //출력방법(JSON)
    //private String outPut;

    //제휴와 관련한 파트너코드, 제휴사의 경우 파트너코드 입력으로 제휴사 유효성 체크
    //private String partner;

    //includeKey가 1인 경우 결과의 상품페이지 링크값에 TTBKey가 포함
    //private String includeKey;

    //검색어의 인코딩 값을 설정 (utf-8, euc-kr)
    //private String inputEncoding;

    //검색 API의 Version을 설정 (최신버젼: 20131101)
    //private String version;

    //1인 경우 최근 한 달내, 0인 경우 전체 기간 출간 상품 검색
    //private String recentPublishFilter;

}
