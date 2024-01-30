package com.bookstore.readme.domain.collection.aladin.request.list;

import com.bookstore.readme.domain.collection.aladin.request.CoverSize;
import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

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

    public Map<String, Object> getQuery(Map<String, Object> query) {
        query.put("QueryType", queryType.getQueryType());
        query.put("SearchTarget", searchTarget == null ? SearchTarget.BOOK : searchTarget.getTarget());
        query.put("SubSearchTarget", subSearchTarget == null ? null : subSearchTarget.getSubSearchTarget());
        query.put("Start", start);
        query.put("MaxResults", maxResults);
        query.put("Cover", cover == null ? null : cover.getCoverSize());
        query.put("outofStockfilter", 0);
        return query;
    }
}
