package com.bookstore.readme.domain.community.exception;

import lombok.Getter;

@Getter
public class NotFoundCommunityByIdException extends CommunityException {
    private final Long noticeId;

    public NotFoundCommunityByIdException(Long noticeId) {
        super(CommunityStatus.NOT_FOUND_COMMUNITY_BY_ID);
        this.noticeId = noticeId;
    }
}
