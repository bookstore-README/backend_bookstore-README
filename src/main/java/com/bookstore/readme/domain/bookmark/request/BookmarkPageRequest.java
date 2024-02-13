package com.bookstore.readme.domain.bookmark.request;

import com.bookstore.readme.domain.bookmark.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookmarkPageRequest {
    //    @NotNull(message = "회원 아이디는 필수 입력해야 합니다.")
//    @Schema(description = "조회할 회원 아이디입니다.", example = "1")
//    private final Integer memberId;
    @Schema(description = "조회할 페이지 번호입니다.", example = "1")
    private final Integer offset;
    @Schema(description = "조회할 페이지당 목록 개수입니다.", example = "10")
    private final Integer limit;
    @Schema(description = "조회할 정렬 조건입니다.", example = "price")
    private final SortType sort;

    public BookmarkPageRequest(Integer offset, Integer limit, String sort) {
//        this.memberId = memberId;
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? 10 : limit;
        this.sort = sort == null ? SortType.ID : SortType.valueOf(sort.toUpperCase());
    }
}
