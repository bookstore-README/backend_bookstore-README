package com.bookstore.readme.domain.community.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommunityPageRequest {
    @Schema(description = "현재 조회를 시작할 아이디입니다.", example = "0")
    private final Integer cursorId;
    @Schema(description = "페이지당 가져올 목록 개수입니다.", example = "10")
    private final Integer limit;

    public CommunityPageRequest(Integer cursorId, Integer limit) {
        this.cursorId = cursorId == null ? 0 : cursorId;
        this.limit = limit == null ? 10 : limit;
    }
}
