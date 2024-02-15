package com.bookstore.readme.domain.community.dto;

import com.bookstore.readme.domain.community.domain.Emoji;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiInfoDto {
    private final String emojiType;
    private final Integer emojiNum;
    private final Boolean memberCheck;
    @Builder
    public EmojiInfoDto(String emojiType, Integer emojiNum, Boolean memberCheck) {
        this.emojiType = emojiType;
        this.emojiNum = emojiNum;
        this.memberCheck = memberCheck;
    }
}
