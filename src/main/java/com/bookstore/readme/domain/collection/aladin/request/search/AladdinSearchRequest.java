package com.bookstore.readme.domain.collection.aladin.request.search;

import com.bookstore.readme.domain.collection.aladin.request.CoverSize;
import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import com.bookstore.readme.domain.collection.aladin.request.SortType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
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

    private Integer categoryId;


    public Map<String, Object> getQuery(Map<String, Object> defaultQuery) {
        defaultQuery.put("query", this.query);
        defaultQuery.put("QueryType", queryType == null ? QueryType.KEYWORD.getType() : queryType.getType());
        defaultQuery.put("SearchTarget", searchTarget == null ? null : searchTarget.getTarget());
        defaultQuery.put("Start", start);
        defaultQuery.put("Cover", cover == null ? null : cover.getCoverSize());
        defaultQuery.put("outofStockfilter", 0);
        defaultQuery.put("MaxResults", maxResults);
        defaultQuery.put("RecentPublishFilter", 0);
        defaultQuery.put("CategoryId", categoryId == null ? 0 : categoryId);
        return defaultQuery;
    }
}
