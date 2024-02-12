package com.bookstore.readme.domain.community.exception;

import lombok.Getter;

@Getter
public enum CommunityStatus {
    NOT_FOUND_COMMUNITY_BY_ID(400, "게시글 아이디와 일치하는 정보가 없습니다."),
    ;

    private final Integer status;
    private final String message;

    CommunityStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
