package com.bookstore.readme.domain.bookmark.request;

import lombok.Getter;

@Getter
public class BookmarkPageRequest {
    private final Integer memberId;
    private final Integer offest;
    private final Integer limit;

    public BookmarkPageRequest(Integer memberId, Integer offset, Integer limit) {
        this.memberId = memberId;
        this.offest = offset;
        this.limit = limit;
    }
}
