package com.bookstore.readme.domain.member.exception;

import lombok.Getter;

@Getter
public class DuplicationMemberException extends MemberException {
    private final Long memberId;

    public DuplicationMemberException(Long memberId) {
        super(MemberStatus.DUPLICATE_MEMBER);
        this.memberId = memberId;
    }
}
