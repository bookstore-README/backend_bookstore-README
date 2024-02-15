package com.bookstore.readme.domain.community.request;

import com.bookstore.readme.domain.community.dto.EmojiType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiRequest {
    @Schema(description = "smile,heart,smile,excited,angry,crying 중 하나", example = "smile")
    @NotNull(message = "이미지 타입은 필수 입니다.")
    private final EmojiType type;

    @Schema(description = "이모지 선택 하면 true 취소면 false", example = "false")
    @NotNull(message = "이미지 타입은 필수 입니다.")
    private final Boolean check;

    @Builder
    public EmojiRequest(String type, Boolean check) {
        this.type = EmojiType.valueOf(type.toUpperCase());
        this.check = check;
    }
}
