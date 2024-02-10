package com.bookstore.readme.domain.bookmark.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class BookmarkDeleteDto {
    @NotNull(message = "삭제할 아이디는 필수 입력값입니다.")
    @Schema(description = "삭제할 찜 아이디 리스트", example = "1,2,3")
    private final List<Integer> bookmarkIds;

    public BookmarkDeleteDto(List<Integer> bookmarkIds) {
        this.bookmarkIds = bookmarkIds;
    }
}
