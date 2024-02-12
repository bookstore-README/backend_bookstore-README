package com.bookstore.readme.domain.community.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommunitySaveRequest {
    @NotNull(message = "회원 아이디는 필수 입력입니다.")
    private final Long memberId;
    @NotNull(message = "도서 아이디는 필수 입력입니다.")
    private final Long bookId;
    @NotEmpty(message = "제목은 필수 입력입니다.")
    private final String title;
    @NotEmpty(message = "내용은 필수 입력입니다.")
    private final String content;

    public CommunitySaveRequest(Long memberId, Long bookId, String title, String content) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.title = title;
        this.content = content;
    }
}
