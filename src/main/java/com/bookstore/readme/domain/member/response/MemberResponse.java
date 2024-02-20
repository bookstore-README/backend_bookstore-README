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

    public static MemberResponse profileError() {
        return new MemberResponse(400, "해당 프로필 파일은 10MB를 넘기면 안됩니다.", null);
    }
}
