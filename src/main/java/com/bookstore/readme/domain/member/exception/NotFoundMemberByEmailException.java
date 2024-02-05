package com.bookstore.readme.domain.member.exception;

import lombok.Getter;

@Getter
public class NotFoundMemberByEmailException extends MemberException {
    private final String email;

    public NotFoundMemberByEmailException(String email) {
        super(MemberStatus.NOT_FOUND_MEMBER_BY_EMAIL);
        this.email = email;
    }
}
