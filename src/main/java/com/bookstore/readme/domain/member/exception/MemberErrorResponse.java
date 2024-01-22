package com.bookstore.readme.domain.member.exception;

public class MemberErrorResponse {
    private final MemberCode memberCode;

    public MemberErrorResponse(MemberCode memberCode) {
        this.memberCode = memberCode;
    }

    public int getStatus() {
        return memberCode.getStatus();
    }

    public String getMessage() {
        return memberCode.getMessage();
    }
}
