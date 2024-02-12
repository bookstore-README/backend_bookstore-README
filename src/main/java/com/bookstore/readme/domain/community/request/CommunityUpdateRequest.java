package com.bookstore.readme.domain.community.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommunityUpdateRequest {
    @Schema(description = "수정할 제목", example = "수정할 제목을 입력!")
    @NotEmpty(message = "수정할 제목은 필수 입력입니다.")
    private final String title;

    @Schema(description = "수정할 내용", example = "수정할 내용을 입력!")
    @NotEmpty(message = "수정할 내용은 필수 입력입니다.")
    private final String content;

    public CommunityUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
