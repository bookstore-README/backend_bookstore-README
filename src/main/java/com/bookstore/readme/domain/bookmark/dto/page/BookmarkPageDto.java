package com.bookstore.readme.domain.bookmark.dto.page;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BookmarkPageDto {
    private final Integer cursorId;
    private final Integer memberId;
    private final Integer total;
    private final Integer limit;
    private final List<BookmarkInfoDto> bookmarks;

    public BookmarkPageDto(Integer cursorId, Integer memberId, Integer total, Integer limit, List<BookmarkInfoDto> bookmarks) {
        this.cursorId = cursorId;
        this.memberId = memberId;
        this.total = total;
        this.limit = limit;
        this.bookmarks = bookmarks;
    }
}
