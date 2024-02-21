package com.bookstore.readme.domain.bookV2.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BookPageRequest {
    private final SortType sortType;
    private final boolean ascending;
    @Schema(description = "무한 스크롤에서 사용, 기본값 -1")
    private final Integer cursorId;
    @Schema(description = "페이지 시작 번호, 기본값 0")
    private final Integer offset;
    @Schema(description = "페이지 당 아이템 개수, 기본값 10")
    private final Integer limit;
    @Schema(description = "책 제목과 작가로 검색할 문장")
    private final String search;

    public BookPageRequest(Integer cursorId, Integer offset, Integer limit, SortType sortType, boolean ascending, String search) {
        this.cursorId = cursorId == null ? -1 : cursorId;
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? 10 : limit;
        this.sortType = sortType == null ? SortType.VIEW : sortType;
        this.ascending = ascending;
        this.search = search;
    }
}
