package com.bookstore.readme.domain.collection.book.request;

import com.bookstore.readme.domain.collection.aladin.request.SearchTarget;
import com.bookstore.readme.domain.collection.aladin.request.search.QueryType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SaveDto {
    @NotEmpty
    private List<String> searches;
    private QueryType queryType;
    private SearchTarget searchTarget;
    @NotEmpty
    private List<Integer> starts;
    private Integer maxResult;

    public SaveDto(List<String> searches, QueryType queryType, SearchTarget searchTarget, List<Integer> starts, Integer maxResult) {
        this.searches = searches;
        this.queryType = queryType == null ? QueryType.KEYWORD : queryType;
        this.searchTarget = searchTarget == null ? SearchTarget.BOOK : searchTarget;
        this.starts = starts;
        this.maxResult = maxResult == null ? 1 : maxResult;
    }
}
