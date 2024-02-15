package com.bookstore.readme.domain.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityPageInfoDto {
    private final Long communityId;
    private final String content;
    private final CommunityMemberDto writer;
    private final CommunityBookDto bookInfo;
    private final EmojiDto emojiInfo;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;


    @Builder
    public CommunityPageInfoDto(Long communityId, String content, CommunityMemberDto writer, CommunityBookDto bookInfo, EmojiDto emojiInfo, LocalDateTime createDate, LocalDateTime updateDate) {
        this.communityId = communityId;
        this.content = content;
        this.writer = writer;
        this.bookInfo = bookInfo;
        this.emojiInfo = emojiInfo;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
