package com.bookstore.readme.domain.review.request;

import com.bookstore.readme.domain.review.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewPageRequest {
    @Schema(description = "현재 페이지 수입니다.(기본값 0)", example = "0")
    private final Integer offset;
    @Schema(description = "페이지 당 보여줄 개수입니다.(기본값 10)", example = "10")
    private final Integer limit;
    @Schema(description = "정렬 방법입니다.(NEWEST, STAR, 기본값 NEWEST)", example = "newest")
    private final SortType sortType;
    @Schema(description = "내림차, 오름차 선택입니다.(true, false 기본값 false)", example = "false")
    private final Boolean ascending;

    @Builder
    public ReviewPageRequest(Integer offset, Integer limit, String sortType, Boolean ascending) {
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? 10 : limit;
        this.sortType = sortType == null ? SortType.NEWEST : SortType.valueOf(sortType.toUpperCase());
        this.ascending = ascending != null && ascending;
    }
}
