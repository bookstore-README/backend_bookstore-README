package com.bookstore.readme.domain.community.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class EmojiDto {
    private final Long emojiId;
    private final List<EmojiInfoDto> emojis;

    public EmojiDto(Long emojiId, List<EmojiInfoDto> emojis) {
        this.emojiId = emojiId;
        this.emojis = emojis;
    }
}
