package com.bookstore.readme.domain.member.response;

import com.bookstore.readme.common.response.CommonResponse;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse extends CommonResponse {

    private final Object data;

    @Builder
    public MemberResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static MemberResponse ok(Object member) {
        return new MemberResponse(200, "Success", member);
    }
}
