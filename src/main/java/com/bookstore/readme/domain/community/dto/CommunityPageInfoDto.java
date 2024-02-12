package com.bookstore.readme.domain.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityPageInfoDto {
    private final Long communityId;
    private final CommunityMemberDto writer;
    private final CommunityBookDto bookInfo;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;

    @Builder
    public CommunityPageInfoDto(Long communityId, CommunityMemberDto writer, CommunityBookDto bookInfo, LocalDateTime createDate, LocalDateTime updateDate) {
        this.communityId = communityId;
        this.writer = writer;
        this.bookInfo = bookInfo;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
