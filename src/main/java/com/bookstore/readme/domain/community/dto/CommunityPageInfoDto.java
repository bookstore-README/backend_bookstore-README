package com.bookstore.readme.domain.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityPageInfoDto {
    private final Long communityId;
    private final String title;
    private final String content;
    private final CommunityMemberDto writer;
    private final CommunityBookDto bookInfo;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    @Builder
    public CommunityPageInfoDto(Long communityId, String title, String content, CommunityMemberDto writer, CommunityBookDto bookInfo, LocalDateTime createDate, LocalDateTime updateDate) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.bookInfo = bookInfo;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
