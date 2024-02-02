package com.bookstore.readme.domain.member.exception;

import lombok.Getter;

@Getter
public enum MemberCode {
    MEMBER_JOIN_FAIL(400, "회원가입에 실패했습니다."),
    DUPLICATE_MEMBER(400, "이미 존재하는 회원 입니다.");

    private final int status;
    private final String message;

    MemberCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
