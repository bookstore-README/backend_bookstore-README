package com.bookstore.readme.domain.bookmark.response;

import com.bookstore.readme.common.response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponse extends CommonResponse {
    private final Object data;

    @Builder
    public BookmarkResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static BookmarkResponse ok(Object data) {
        return new BookmarkResponse(200, "Success", data);
    }


}
