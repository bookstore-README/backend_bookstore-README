package com.bookstore.readme.domain.community.exception;

import lombok.Getter;

@Getter
public enum NoticeStatus {
    NOT_FOUND_NOTICE_BY_ID(400, "게시글 아이디와 일치하는 정보가 없습니다."),
    ;

    private final Integer status;
    private final String message;

    NoticeStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
