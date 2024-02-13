package com.bookstore.readme.domain.community.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
public class CommunityUpdateRequest {
    @Schema(description = "수정할 내용", example = "수정할 내용을 입력!")
    @NotEmpty(message = "수정할 내용은 필수 입력입니다.")
    private  String content;

    public CommunityUpdateRequest() {
    }

    public CommunityUpdateRequest(String content) {
        this.content = content;
    }
}
