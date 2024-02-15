package com.bookstore.readme.domain.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiUpdateDto {
    private final Long memberEmojiId;
    private final Long memberId;
    private final Long communityId;

    @Builder
    public EmojiUpdateDto(Long memberEmojiId, Long memberId, Long communityId, EmojiDto emojiDto) {
        this.memberEmojiId = memberEmojiId;
        this.memberId = memberId;
        this.communityId = communityId;
    }
}
