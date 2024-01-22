package com.bookstore.readme.domain.member.exception;

import lombok.Getter;

@Getter
public class DuplicationMemberException extends MemberException {
    private final MemberErrorResponse memberErrorResponse;

    public DuplicationMemberException(MemberErrorResponse memberErrorResponse) {
        this.memberErrorResponse = memberErrorResponse;
    }
}
