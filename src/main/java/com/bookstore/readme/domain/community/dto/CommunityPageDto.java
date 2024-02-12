package com.bookstore.readme.domain.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommunityPageDto {
    private final Integer total;
    private final Integer limit;
    private final Integer cursorId;

    private final List<CommunityPageInfoDto> cards;

    @Builder
    public CommunityPageDto(Integer total, Integer limit, Integer cursorId, List<CommunityPageInfoDto> cards) {
        this.total = total;
        this.limit = limit;
        this.cursorId = cursorId;
        this.cards = cards;
    }
}
